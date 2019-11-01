using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

using PlantDexAPI.Models;
namespace PlantDexAPI.Controllers
{
    public class AdminModeController : ApiController
    {
        [HttpPost]
        public HttpResponseMessage HandleAdminRequest(PlantDexHttpRequest request)
        {


            return Request.CreateResponse(HttpStatusCode.OK, "fail");
        }
    }
}
