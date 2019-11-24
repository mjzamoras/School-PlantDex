using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

using PlantDexAPI.Models;

using System.IO;
namespace PlantDexAPI.Controllers
{
    public class ClientFunctionsController : ApiController
    {
        [HttpPost]
        public HttpResponseMessage ClassifyPlant(PlantDexHttpRequest request)
        {
            PlantDexHttpResponse response = new PlantDexHttpResponse();
            if (request == null)
            {
                response.message = "Invalid Request Parameters Given!";
                response.status = "Invalid Request";
                response.data = null;
                return Request.CreateResponse(HttpStatusCode.BadRequest, response);
            }

            if (PlantDexHttpRequestManager.AuthenticateRequest(request) == "fail")
            {
                response.message = "UnAuthorized Request";
                response.status = "Invalid Request";
                response.data = null;
                return Request.CreateResponse(HttpStatusCode.Unauthorized, response);
            }
            
            
        }


        
    }
}
