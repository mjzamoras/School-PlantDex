using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PlantDexAPI.Models
{
    public class HttpRequest_Admin
    {
        public string requestSource { get; set; }
        public string requestCommand { get; set; }
        public string requestIdentity { get; set; }
        public List<Object> data { get; set; }
    }
}