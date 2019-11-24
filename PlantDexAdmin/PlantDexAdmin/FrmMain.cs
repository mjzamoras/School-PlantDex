using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace PlantDexAdmin
{
    public partial class FrmMain : Form
    {
        public FrmMain()
        {
            InitializeComponent();
        }

        private void addAdminToolStripMenuItem_Click(object sender, EventArgs e)
        {

        }

        private void addPlantToolStripMenuItem_Click(object sender, EventArgs e)
        {

        }

        private void addPlantIDToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ClearForms();
           
        }

        private void ClearForms()
        {
            foreach (Form frm in this.MdiChildren)
            {
                frm.Close();
            }
        }
    }
}
