using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PlantDexAPI.Models
{
    public static class GlobalEssentials
    {
        public static string ADMIN_AUTH_KEY = "UGxhbnREZXhBZG1pbg==";        //PlantDexAdmin -> base64 encode
        public static string CLIENT_AUTH_KEY = "UGxhbnREZXhDbGllbnQ=";       //PlantDexClient -> base64 encode

        public static void UpdateAuthKey(string newKey, string target)
        {
            if (target == "__admin__")
            {
                ADMIN_AUTH_KEY = newKey;
            }
            else if(target == "__client__")
            {
                CLIENT_AUTH_KEY = newKey;
            }
        }

    }
}