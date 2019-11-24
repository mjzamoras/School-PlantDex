namespace PlantDexAdmin
{
    partial class FrmAddPlantImage
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FrmAddPlantImage));
            this.label1 = new System.Windows.Forms.Label();
            this.txtPlantID = new System.Windows.Forms.TextBox();
            this.img = new System.Windows.Forms.PictureBox();
            this.Button3 = new System.Windows.Forms.Button();
            this.button1 = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.img)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(27, 27);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(54, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Plant ID : ";
            this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // txtPlantID
            // 
            this.txtPlantID.Location = new System.Drawing.Point(77, 24);
            this.txtPlantID.Name = "txtPlantID";
            this.txtPlantID.Size = new System.Drawing.Size(206, 20);
            this.txtPlantID.TabIndex = 1;
            // 
            // img
            // 
            this.img.BackColor = System.Drawing.SystemColors.ActiveBorder;
            this.img.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("img.BackgroundImage")));
            this.img.Location = new System.Drawing.Point(306, 24);
            this.img.Name = "img";
            this.img.Size = new System.Drawing.Size(296, 271);
            this.img.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.img.TabIndex = 11;
            this.img.TabStop = false;
            // 
            // Button3
            // 
            this.Button3.BackColor = System.Drawing.SystemColors.AppWorkspace;
            this.Button3.Location = new System.Drawing.Point(374, 301);
            this.Button3.Name = "Button3";
            this.Button3.Size = new System.Drawing.Size(158, 30);
            this.Button3.TabIndex = 12;
            this.Button3.Text = "Set image";
            this.Button3.UseVisualStyleBackColor = false;
            this.Button3.Click += new System.EventHandler(this.Button3_Click);
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(208, 50);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 13;
            this.button1.Text = "Add";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // FrmAddPlantImage
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(614, 384);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.Button3);
            this.Controls.Add(this.img);
            this.Controls.Add(this.txtPlantID);
            this.Controls.Add(this.label1);
            this.Name = "FrmAddPlantImage";
            this.Text = "Admin - Add Plant Image";
            ((System.ComponentModel.ISupportInitialize)(this.img)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtPlantID;
        internal System.Windows.Forms.PictureBox img;
        internal System.Windows.Forms.Button Button3;
        private System.Windows.Forms.Button button1;
    }
}