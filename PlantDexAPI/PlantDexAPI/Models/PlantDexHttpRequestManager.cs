using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

using System.IO;
using System.Data.SqlClient;
using System.Data.SqlTypes;
using System.Text;
using System.Security.Cryptography;
namespace PlantDexAPI.Models
{
    public static class PlantDexHttpRequestManager
    {
        public static string AuthenticateRequest(PlantDexHttpRequest request)
        {
            string cLevel = request.commandLevel;
            string source = request.source;
            string command = request.command;
            string digest = request.digest;
            string rawDigest;

            if (cLevel == "admin")
            {
                rawDigest = cLevel + ":" + source + ":" + command + ":" + GlobalEssentials.ADMIN_AUTH_KEY;
            }
            else if(cLevel == "client")
            {
                rawDigest = cLevel + ":" + source + ":" + command + ":" + GlobalEssentials.CLIENT_AUTH_KEY;
            }
            else
            {
                return "fail";
            }

            if (Hash(rawDigest) == digest)
            {
                return "success";
            }

            return "fail";
            
        }

        public static PlantDexHttpResponse InsertAdminAccount(AdminAccount account)
        {
            PlantDexHttpResponse response = new PlantDexHttpResponse();
            response.status = "FAIL";
            response.message = "WEB API ERROR";
            try
            {
                SqlConnection con = ConnectionManager.GetConnection();
                SqlCommand com = new SqlCommand("INSERT INTO Admins( FirstName, MI, LastName, Username, Password, timestamp, encoded_by) VALUES( @FirstName, @MI, @LastName, @Username, @Password, @timestamp, @encoded_by)", con);
                com.Parameters.AddWithValue("@FirstName", account.FirstName);
                com.Parameters.AddWithValue("@MI", account.MI);
                com.Parameters.AddWithValue("@LastName", account.LastName);
                com.Parameters.AddWithValue("@Username", account.Username);
                com.Parameters.AddWithValue("@Password", account.Password);
                com.Parameters.AddWithValue("@timestamp", account.timestamp);
                com.Parameters.AddWithValue("@encoded_by", account.encoded_by);
                com.ExecuteNonQuery();

                response.status = "SUCCESS";
                response.message = "Successfully Added Admin Account";
            }
            catch (Exception ex)
            {
                response.message = ex.Message;
            }

            return response;
        }

        private static string Hash(string rawDigest)
        {
            string newDigest = null;

            try
            {
                var sha1 = SHA1Managed.Create();
                byte[] inputBytes = Encoding.ASCII.GetBytes(rawDigest);
                byte[] outputBytes = sha1.ComputeHash(inputBytes);
                return BitConverter.ToString(outputBytes).Replace("-", "").ToLower();
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error Occured (ZEEND_RequestManager/Hash) : " + ex.Message);
            }

            Console.WriteLine(newDigest);

            return newDigest;
        }
    }
}