using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;

namespace PlantDexAPI
{
    public static class WebApiConfig
    {
        public static void Register(HttpConfiguration config)
        {
            // Web API configuration and services

            // Web API routes
            config.MapHttpAttributeRoutes();

            config.Routes.MapHttpRoute(
                name: "PlantDexAdminAPI",
                routeTemplate: "api/PlantDexAdmin/{controller}/{action}",
                defaults: new { action = RouteParameter.Optional, controller="AdminMode" }
            );

            config.Routes.MapHttpRoute(
                name: "PlantDexAPI",
                routeTemplate: "api/PlantDex/{controller}/{action}",
                defaults: new { action = RouteParameter.Optional, controller="ClientFunctions"}
            );
        }
    }
}
