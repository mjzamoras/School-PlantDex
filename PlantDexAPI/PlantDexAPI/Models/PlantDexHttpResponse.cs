using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PlantDexAPI.Models
{
    public class PlantDexHttpResponse
    {
        public string status { get; set; }
        public string message { get; set; }
        public List<Object> data { get; set; }
    }
}