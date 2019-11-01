using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PlantDexAPI.Models
{
    public class AdminAccount
    {
        public string uid { get; set; }
        public string FirstName { get; set; }
        public string MI { get; set; }
        public string LastName { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public string timestamp { get; set; }
        public string encoded_by { get; set; }
    }
}