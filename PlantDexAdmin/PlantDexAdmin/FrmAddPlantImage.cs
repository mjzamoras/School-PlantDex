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
    public partial class FrmAddPlantImage : Form
    {
        public FrmAddPlantImage()
        {
            InitializeComponent();
        }

        private void Button3_Click(object sender, EventArgs e)
        {
            OpenFileDialog openFile = new OpenFileDialog();
            openFile.Filter = "Choose Image(*.JPG;*.PNG;*.GIF)|*.jpg;*.png;*.gif";
            if (openFile.ShowDialog() == DialogResult.OK)
            {
                img.Image = Image.FromFile(openFile.FileName);
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            AddImage();
        }

        private void AddImage()
        {
            try
            {
                string plantID = txtPlantID.Text.ToString();
                Image img2 = img.Image;
                if (img2 == null)
                {
                    throw new Exception("Please SEt An Image First!");
                }
                SqlConnection con = ConnectionManager.GetConnection();
                SqlCommand com = new SqlCommand("SELECT * FROM PlantID WHERE uid = @uid", con);
                com.Parameters.AddWithValue("@uid", plantID);
                using (SqlDataReader read = com.ExecuteReader())
                {
                    if (!read.HasRows)
                    {
                        throw new Exception("Please Input A Valid PlantID!");
                    }

                }
                con.Close();
                con.Open();
                com = new SqlCommand("INSERT INTO PlantImages(PlantID, Image, encoded_by, timestamp) VALUES(@PlantID, @Image, @encoded_by, GETDATE())", con);
                com.Parameters.AddWithValue("@PlantID", plantID);
                com.Parameters.AddWithValue("@encoded_by", "sysgen");
                MemoryStream ms = new MemoryStream();
                img.Image.Save(ms, img.Image.RawFormat);
                com.Parameters.Add("@Image", SqlDbType.Image).Value = ms.ToArray();
                com.ExecuteNonQuery();
                con.Close();
                MessageBox.Show("Successfully Saved Image!");
                txtPlantID.Text = "";
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
