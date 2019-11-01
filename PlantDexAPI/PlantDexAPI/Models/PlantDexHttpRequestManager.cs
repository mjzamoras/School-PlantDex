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