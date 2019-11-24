using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PlantDexAPI.Models
{
    public class PlantDexHttpRequest
    {
        public string commandLevel { get; set; }
        public string source { get; set; }
        public string command { get; set; }
        public string digest { get; set; }
        public List<Object> data { get; set; }
    }
}