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
            PlantDexHttpResponse response = new PlantDexHttpResponse();
            response.status = "FAIL";
            response.message = "Web API Error";
            
            if (request == null)
            {
                response.message = "Invalid Request Parameters";
                return Request.CreateResponse(HttpStatusCode.OK, response);
            }

            if (PlantDexHttpRequestManager.AuthenticateRequest(request) == "fail")
            {
                response.message = "Invalid Request Sender";
                return Request.CreateResponse(HttpStatusCode.OK, response);
            }

            return Request.CreateResponse(HttpStatusCode.OK, "success");
        }
    }
}
