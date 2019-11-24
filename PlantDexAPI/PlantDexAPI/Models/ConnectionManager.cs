using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

using System.IO;
using System.Data.SqlClient;
using System.Data.SqlTypes;
namespace PlantDexAPI.Models
{
    public static class ConnectionManager
    {
        public static SqlConnection GetConnection()
        {
            SqlConnection con = new SqlConnection();
            con.ConnectionString = Properties.Settings.Default.DBConnection;
            con.Open();
            return con;
        }
    }
}