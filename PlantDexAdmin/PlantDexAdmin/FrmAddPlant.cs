using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

using System.IO;
using System.Data.SqlClient;
using System.Data.SqlTypes;
namespace PlantDexAdmin
{
    public partial class FrmAddPlant : Form
    {
        public FrmAddPlant()
        {
            InitializeComponent();
        }

        private void gridPlants_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void FrmAddPlant_Load(object sender, EventArgs e)
        {
            timer1.Start();
            LoadPlantData();
        }


        private void LoadPlantData()
        {
            try
            {
                SqlConnection con = ConnectionManager.GetConnection();
                SqlCommand com = new SqlCommand("SELECT * FROM SummarizedPlantData ORDER BY CommonName ASC", con);
                DataTable data = new DataTable();
                using (SqlDataAdapter adap = new SqlDataAdapter(com))
                {
                    adap.Fill(data);
                }
                gridPlants.DataSource = data;
            }
            catch (Exception ex)
            {
                MessageBox.Show("FrmAddPlant_LoadPlantData Error : " + ex.Message);
            }
        }

        private void AddPlantID()
        {
            try
            {
                string scientificName = txtScientificName.Text.ToString();
                string commonName = txtCommonName.Text.ToString();
                string color = txtColor.Text.ToString();
                string edible = txtEdible.Text.ToString();
                string size = txtSize.Text.ToString();
                string rarity = txtRarity.Text.ToString();

                if(scientificName.Length < 2 || commonName.Length < 2 || color.Length < 2 ||
                    edible.Length < 2 || size.Length < 2 || rarity.Length < 2)
                {
                    MessageBox.Show("Please Fill In Legitimate Data!");
                    return;
                }

                SqlConnection con = ConnectionManager.GetConnection();
                SqlCommand com = new SqlCommand("SELECT * FROM SummarizedPlantData WHERE ScientificName = @name", con);
                com.Parameters.AddWithValue("@name", scientificName);
                using (SqlDataReader read = com.ExecuteReader())
                {
                    if (read.HasRows)
                    {
                        MessageBox.Show("Addition Failed : Plant Already Exists in Database!");
                        return;
                    }
                }
                
                con.Close();
                com = new SqlCommand("INSERT INTO PlantID(ScientificName, timestamp, encoded_by, CommonName) VALUES(@ScientificName, GETDATE(), 'sysgen', @CommonName) SELECT SCOPE_IDENTITY()", con);
                com.Parameters.AddWithValue("@ScientificName", scientificName);
                com.Parameters.AddWithValue("@CommonName", commonName);
                string i = com.ExecuteScalar().ToString();
                con.Close();

                con.Open();
                com = new SqlCommand("INSERT INTO PlantDetails(PlantID, Color, Edible, Size, Rarity) VALUES(@PlantID, @Color, @Edible, @Size, @Rarity)", con);
                com.Parameters.AddWithValue("@PlantID", i);
                com.Parameters.AddWithValue("@Color", color);
                com.Parameters.AddWithValue("@Edible", edible);
                com.Parameters.AddWithValue("@Size", size);
                com.Parameters.AddWithValue("@Rarity", rarity);
                com.ExecuteNonQuery();
                con.Close();

                MessageBox.Show("Successfully Saved Plant!");

                txtScientificName.Text = "";
                txtCommonName.Text = "";
                txtColor.Text = "";
                txtEdible.Text = "";
                txtRarity.Text = "";
                txtSize.Text = "";
            }
            catch (Exception ex)
            {
                MessageBox.Show("FrmAddPlant_AddPlantID Error : " + ex.Message);
            }
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            LoadPlantData();
        }

        private void btnSave_Click(object sender, EventArgs e)
        {
            AddPlantID();
        }
    }
}
