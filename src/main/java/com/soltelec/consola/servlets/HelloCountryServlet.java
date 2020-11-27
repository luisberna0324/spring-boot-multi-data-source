package com.soltelec.consola.servlets;

import com.soltelec.consola.facade.ServiciosBasicoFacade;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

@WebServlet(urlPatterns = "/ServicioBasicoControler.do", loadOnStartup = 1)
public class HelloCountryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

@Autowired
private ServiciosBasicoFacade serviciosBasicoFacade;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Caused by: org.hibernate.AnnotationException: mappedBy reference an unknown target entity property: com.soltelec.entities.Ciudad.departamento in com.soltelec.entities.Departamento.ciudades at org.hibernate.cfg.annotations.CollectionBinder.bindStarToManySecondPass(CollectionBinder.java:768) at org.hibernate.cfg.annotations.CollectionBinder$1.secondPass(CollectionBinder.java:728) at org.hibernate.cfg.CollectionSecondPass.doSecondPass(CollectionSecondPass.java:70) at

		PrintWriter out =null;
		ServletOutputStream outs =null;
		String entidad = request.getParameter("entidad");
		if(entidad==null) {
			entidad = "findAvatar";
		}
		if (entidad.equalsIgnoreCase("findAvatar")) {
			response.setContentType("image/png");
			//outs = response.getOutputStream();
		}else {
			response.setContentType("text/html;charset=UTF-8");
			out =  response.getWriter();
		}
		String respuestaEntidad = "0";
		ServletContext context = getServletContext();
		String contexPath = context.getContextPath();

		try {

			if (entidad.equalsIgnoreCase("autentificacion")) {
				StringTokenizer cadenaAtributos = new StringTokenizer(request.getParameter("cadenaAtributos"), ";");
				respuestaEntidad = serviciosBasicoFacade.valAutUserDominio(cadenaAtributos.nextToken(),	cadenaAtributos.nextToken());
				if (!respuestaEntidad.equalsIgnoreCase("no")) {
					HttpSession session = request.getSession();
					StringTokenizer cadenaSession = new StringTokenizer(respuestaEntidad, ";");
					session.setAttribute("cedula", cadenaSession.nextToken());
					session.setAttribute("nombreUsuario", cadenaSession.nextToken());
					session.setAttribute("nickUsuario", cadenaSession.nextToken());
					String rolUser= cadenaSession.nextToken();
					session.setAttribute("rolUsuario",rolUser);
					session.setAttribute("identificador",session.getAttribute("cedula"));
					session.setMaxInactiveInterval(30);
					cadenaSession.nextToken();
					session.setAttribute("instancia",cadenaSession.nextToken());
					session.setAttribute("fkRevision","0");
					if(rolUser.equalsIgnoreCase("PRN") || rolUser.equalsIgnoreCase("ATC")){
						session.setMaxInactiveInterval(810);
					}
					if(rolUser.equalsIgnoreCase("IPR")) {
						session.setMaxInactiveInterval(910);
					}
				}
			}

			if (entidad.equalsIgnoreCase("servCtxShowDefectos")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servCtxShowDefectos(request.getParameter("cadenaAtributos"));
				}
			}

			if (entidad.equalsIgnoreCase("servFindCtxFotos")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servFindCtxFotos(request.getParameter("cadenaAtributos"));
				}
			}

			if (entidad.equalsIgnoreCase("viewControlSectLiviano")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					if (session.getAttribute("seccionLabradoLiviano") == null) {
						respuestaEntidad = "0";
					}else {
						respuestaEntidad = "1";
					}
					if (session.getAttribute("fijarCtxLadoRef") == null) {
						respuestaEntidad = "0";
					}else {
						respuestaEntidad = respuestaEntidad.concat(";").concat((String)session.getAttribute("fijarCtxLadoRef"));
					}
					if (session.getAttribute("particionLiviano1") == null) {
						respuestaEntidad = respuestaEntidad.concat(";").concat("0");
					}else {
						respuestaEntidad = respuestaEntidad.concat(";").concat("1");
					}

					if (session.getAttribute("particionLiviano2") == null) {
						respuestaEntidad = respuestaEntidad.concat(";").concat("0");
					}else {
						respuestaEntidad = respuestaEntidad.concat(";").concat("1");
					}
					if (session.getAttribute("particionLiviano3") == null) {
						respuestaEntidad = respuestaEntidad.concat(";").concat("0");
					}else {
						respuestaEntidad = respuestaEntidad.concat(";").concat("1");
					}

					if (session.getAttribute("particionLiviano4") == null) {
						respuestaEntidad = respuestaEntidad.concat(";").concat("0");
					}else {
						respuestaEntidad = respuestaEntidad.concat(";").concat("1");
					}

					if (session.getAttribute("particionLivianoEnse") == null) {
						respuestaEntidad = respuestaEntidad.concat(";").concat("0");
					}else {
						respuestaEntidad = respuestaEntidad.concat(";").concat("1");
					}

					if (session.getAttribute("particionLivianoTaxi") == null) {
						respuestaEntidad = respuestaEntidad.concat(";").concat("0");
					}else {
						respuestaEntidad = respuestaEntidad.concat(";").concat("1");
					}

					if (session.getAttribute("particionPublico") == null) {
						respuestaEntidad = respuestaEntidad.concat(";").concat("0");
					}else {
						respuestaEntidad = respuestaEntidad.concat(";").concat("1");
					}

					if (session.getAttribute("ctxLLDD") == null) {
						respuestaEntidad = respuestaEntidad.concat(";").concat("0");
					}else {
						respuestaEntidad = respuestaEntidad.concat(";").concat("1");
					}

					if (session.getAttribute("ctxLLDI") == null) {
						respuestaEntidad = respuestaEntidad.concat(";").concat("0");
					}else {
						respuestaEntidad = respuestaEntidad.concat(";").concat("1");
					}

					if (session.getAttribute("ctxLLTI") == null) {
						respuestaEntidad = respuestaEntidad.concat(";").concat("0");
					}else {
						respuestaEntidad = respuestaEntidad.concat(";").concat("1");
					}
					if (session.getAttribute("ctxLLTD") == null) {
						respuestaEntidad = respuestaEntidad.concat(";").concat("0");
					}else {
						respuestaEntidad = respuestaEntidad.concat(";").concat("1");
					}

					if (session.getAttribute("ctxLLResp") == null) {
						respuestaEntidad = respuestaEntidad.concat(";").concat("0");
					}else {
						respuestaEntidad = respuestaEntidad.concat(";").concat("1");
					}

				}
			}

			if (entidad.equalsIgnoreCase("SectControlLabrado")) {
				HttpSession session = request.getSession(false);

				if (session == null) {
					respuestaEntidad = "2";
				} else {
					StringTokenizer flujo = new StringTokenizer(request.getParameter("cadenaAtributos"), "!");
					StringTokenizer flujoPrueba = new StringTokenizer(flujo.nextToken(), "/");
					String ctxPrueba = flujoPrueba.nextToken();
					String ctxMinLL = flujo.nextToken();
					String strProfundidad = flujoPrueba.nextToken();
					StringTokenizer proLLantas = new StringTokenizer(strProfundidad, ",");
					String ctxLLanta = proLLantas.nextToken();
					String ctxLL1 = proLLantas.nextToken();
					String ctxLL2 = proLLantas.nextToken();
					String ctxLL3 = proLLantas.nextToken();
					String ctxLL4 = proLLantas.nextToken();

					/*if (session.getAttribute(ctxLLanta) == null) {
						session.setAttribute(ctxLLanta, ctxLLanta);
					} */


					/*if (session.getAttribute(ctxLLanta) == null) {
					     session.setAttribute(ctxLLanta, ctxLLanta);
				    } */
					session.setAttribute(ctxLLanta, ctxLLanta);
					session.setAttribute(ctxLLanta.concat("prof1"), ctxLL1);
					session.setAttribute(ctxLLanta.concat("prof2"), ctxLL2);
					session.setAttribute(ctxLLanta.concat("prof3"), ctxLL3);
					session.setAttribute(ctxLLanta.concat("prof4"), ctxLL4);
					session.setAttribute(ctxLLanta.concat("min"), ctxMinLL);
					int eve=0;
					if(session.getAttribute("ctxLLDD")!=null) {
						eve++;
					}
					if(session.getAttribute("ctxLLResp")!=null) {
						eve++;
					}
					if(session.getAttribute("ctxLLTI")!=null) {
						eve++;
					}
					if(session.getAttribute("ctxLLTD")!=null) {
						eve++;
					}
					if(session.getAttribute("ctxLLDI")!=null) {
						eve++;
					}


					if(session.getAttribute("ctxLLDD")!=null && session.getAttribute("ctxLLDI")!=null && session.getAttribute("ctxLLResp")!=null&& session.getAttribute("ctxLLTI")!=null&& session.getAttribute("ctxLLTD")!=null) {
						String sendProf = ctxPrueba.concat("/").concat((String)session.getAttribute("ctxLLDD".concat("prof1"))).concat(",").concat((String)session.getAttribute("ctxLLDD".concat("prof2"))).concat(",").concat((String)session.getAttribute("ctxLLDD".concat("prof3"))).concat(",").concat((String)session.getAttribute("ctxLLDD".concat("prof4"))).concat(",").concat((String)session.getAttribute("ctxLLDI".concat("prof1"))).concat(",").concat((String)session.getAttribute("ctxLLDI".concat("prof2"))).concat(",").concat((String)session.getAttribute("ctxLLDI".concat("prof3"))).concat(",").concat((String)session.getAttribute("ctxLLDI".concat("prof4"))).concat(",") .concat((String)session.getAttribute("ctxLLTD".concat("prof1"))).concat(",").concat((String)session.getAttribute("ctxLLTD".concat("prof2"))).concat(",").concat((String)session.getAttribute("ctxLLTD".concat("prof3"))).concat(",").concat((String)session.getAttribute("ctxLLTD".concat("prof4"))).concat(",") .concat((String)session.getAttribute("ctxLLTI".concat("prof1"))).concat(",").concat((String)session.getAttribute("ctxLLTI".concat("prof2"))).concat(",").concat((String)session.getAttribute("ctxLLTI".concat("prof3"))).concat(",").concat((String)session.getAttribute("ctxLLTI".concat("prof4"))).concat("!").concat((String)session.getAttribute("ctxLLResp".concat("prof1"))).concat(",").concat((String)session.getAttribute("ctxLLResp".concat("prof2"))).concat(",").concat((String)session.getAttribute("ctxLLResp".concat("prof3"))).concat(",").concat((String)session.getAttribute("ctxLLResp".concat("prof4"))).concat("!").concat((String)session.getAttribute("ctxLLDD".concat("min"))).concat("$").concat((String)session.getAttribute("ctxLLDI".concat("min"))).concat("$").concat((String)session.getAttribute("ctxLLTD".concat("min"))).concat("$").concat((String)session.getAttribute("ctxLLTI".concat("min"))).concat("$").concat((String)session.getAttribute("ctxLLResp".concat("min"))).concat("$") ;
						respuestaEntidad = serviciosBasicoFacade.serPersitProfLabradoLiviano(sendProf);
						session.setAttribute("seccionLabradoLiviano","1");
					}else {
						respuestaEntidad=ctxLLanta;
					}
				}
			}

			if (entidad.equalsIgnoreCase("viewControlSectMoto")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					if (session.getAttribute("seccionFrenoMoto") == null) {
						respuestaEntidad = "0";
					}else {
						respuestaEntidad = "1";
					}

					if (session.getAttribute("seccionMecanizadaMoto") == null) {
						respuestaEntidad = respuestaEntidad.concat(";").concat("0");
					}else {
						respuestaEntidad = respuestaEntidad.concat(";").concat("1");
					}

					if (session.getAttribute("seccionExteriorMoto") == null) {
						respuestaEntidad = respuestaEntidad.concat(";").concat("0");
					}else {
						respuestaEntidad = respuestaEntidad.concat(";").concat("1");
					}

					if (session.getAttribute("seccionLabrado") == null) {
						respuestaEntidad = respuestaEntidad.concat(";").concat("0");
					}else {
						respuestaEntidad = respuestaEntidad.concat(";").concat("1");
					}

					if (session.getAttribute("particionMotoEnse") == null) {
						respuestaEntidad = respuestaEntidad.concat(";").concat("0");
					}else {
						respuestaEntidad = respuestaEntidad.concat(";").concat("1");
					}

				}
			}



			if (entidad.equalsIgnoreCase("setSeccionExteriorMoto")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					session.setAttribute("seccionExteriorMoto","1");
					respuestaEntidad = "entro";
				}
			}

			if (entidad.equalsIgnoreCase("setParticion1Liviano")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					session.setAttribute("particionLiviano1","1");
					respuestaEntidad = "entro";
				}
			}
			if (entidad.equalsIgnoreCase("setParticion2Liviano")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					session.setAttribute("particionLiviano2","1");
					respuestaEntidad = "entro";
				}
			}

			if (entidad.equalsIgnoreCase("setParticion3Liviano")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					session.setAttribute("particionLiviano3","1");
					respuestaEntidad = "entro";
				}
			}

			if (entidad.equalsIgnoreCase("setParticion4Liviano")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					session.setAttribute("particionLiviano4","1");
					respuestaEntidad = "entro";
				}
			}



			if (entidad.equalsIgnoreCase("setParticionEnseLiviano")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					session.setAttribute("particionLivianoEnse","1");
				}
			}

			if (entidad.equalsIgnoreCase("setParticionEnseMoto")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					session.setAttribute("particionMotoEnse","1");
				}
			}

			if (entidad.equalsIgnoreCase("setParticionTaxiLiviano")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					session.setAttribute("particionLivianoTaxi","1");
					respuestaEntidad = "entro";
				}
			}

			if (entidad.equalsIgnoreCase("setParticionPublico")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					session.setAttribute("particionPublico","1");
					respuestaEntidad = "entro";
				}
			}


			if (entidad.equalsIgnoreCase("setSeccionLabrado")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					session.setAttribute("seccionLabrado","1");
					respuestaEntidad = "entro";
				}
			}

			if (entidad.equalsIgnoreCase("setSeccionLabradoLiviano")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					session.setAttribute("seccionLabradoLiviano","1");
					respuestaEntidad = "entro";
				}
			}


			if (entidad.equalsIgnoreCase("setSeccionFrenoMoto")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					session.setAttribute("seccionFrenoMoto","1");
					respuestaEntidad = "entro";
				}
			}

			if (entidad.equalsIgnoreCase("setSeccionMecanizadaMoto")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					session.setAttribute("seccionMecanizadaMoto","1");
					respuestaEntidad = "entro";
				}
			}

			if (entidad.equalsIgnoreCase("setCertPreRevision")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					String usuario =(String)session.getAttribute("identificador");
					String[] atrrData = request.getParameter("cadenaAtributos").split(";");
					respuestaEntidad = serviciosBasicoFacade.setFechaInicioRecepcion(Integer.parseInt(atrrData[0]),Integer.parseInt(usuario));
					session.setAttribute("CertPreRevision",atrrData[0]);
					session.setAttribute("ctxRevision",atrrData[1]);
					session.setAttribute("nroFactura",respuestaEntidad);
				}
			}

			if (entidad.equalsIgnoreCase("evalStatusSession")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = "1";
				}
			}

			if (entidad.equalsIgnoreCase("servFindDepartametos")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servFindDepartametos();
				}
			}

			if (entidad.equalsIgnoreCase("setCierrePrueba")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servSetCierrePrueba(request.getParameter("cadenaAtributos"));
					session.removeAttribute("seccionFrenoMoto");
					session.removeAttribute("seccionMecanizadaMoto");
					session.removeAttribute("seccionExteriorMoto");
					session.removeAttribute("seccionLabrado");
					session.removeAttribute("particionMotoEnse");
					session.removeAttribute("seccionLabradoLiviano");
					session.removeAttribute("particionLiviano1");
					session.removeAttribute("particionLiviano2");
					session.removeAttribute("particionLiviano3");
					session.removeAttribute("particionLiviano4");
					session.removeAttribute("particionLivianoEnse");
					session.removeAttribute("particionLivianoTaxi");
					session.removeAttribute("particionPublico");
				}
			}

			if (entidad.equalsIgnoreCase("servSetDefectoPrueba")) {
				HttpSession session1 = request.getSession(false);
				if (session1 == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servSetDefectoPrueba(request.getParameter("cadenaAtributos"));
				}
			}


			if (entidad.equalsIgnoreCase("servFindColoresByName")) {
				HttpSession session1 = request.getSession(false);
				if (session1 == null) {
					respuestaEntidad = "2";
				} else {
					String eve =request.getParameter("cadenaAtributos");
					respuestaEntidad = serviciosBasicoFacade.BusqColoresExist(eve);
				}
			}
			if (entidad.equalsIgnoreCase("servFindMarcaByName")) {
				HttpSession session1 = request.getSession(false);
				if (session1 == null) {
					respuestaEntidad = "2";
				} else {
					String eve =request.getParameter("cadenaAtributos");
					respuestaEntidad = serviciosBasicoFacade.busqMarcaExist(eve);
				}
			}

			if (entidad.equalsIgnoreCase("servFindAseguradoraByName")) {
				HttpSession session1 = request.getSession(false);
				if (session1 == null) {
					respuestaEntidad = "2";
				} else {
					String eve =request.getParameter("cadenaAtributos");
					respuestaEntidad = serviciosBasicoFacade.busqAseguradoraExist(eve);
				}
			}

			if (entidad.equalsIgnoreCase("servFindEstadoPruebas")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servFindEstadoPruebas(request.getParameter("cadenaAtributos"));
				}
			}

			if (entidad.equalsIgnoreCase("servFindCiudades")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servFindCiudades(Integer.parseInt(request.getParameter("cadenaAtributos")));
				}
			}

			if (entidad.equalsIgnoreCase("servRegObservacionPruebas")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servSetObservacionesPruebas(request.getParameter("cadenaAtributos"));
				}
			}

			if (entidad.equalsIgnoreCase("servFindAtrrCDA")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servFindCtxCDA();
				}
			}

			if (entidad.equalsIgnoreCase("setPanelConfiguracionCda")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.setPanelConfiguracionCda(request.getParameter("cadenaAtributos"));
				}
			}

			if (entidad.equalsIgnoreCase("servFindStatusPruebas")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servFindStatusPruebas(request.getParameter("cadenaAtributos"));

				}
			}
			if (entidad.equalsIgnoreCase("serPersitProfLabrado")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.serPersitProfLabrado(request.getParameter("cadenaAtributos"));

				}
			}



			if (entidad.equalsIgnoreCase("servCapturaFecha")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					Calendar dayNow = Calendar.getInstance();
					respuestaEntidad = String.valueOf(dayNow.getTime().getTime()) ;
				}
			}

			if (entidad.equalsIgnoreCase("servFindHojaPruebasAbiertas")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servFindHojaPruebasAbiertas(request.getParameter("cadenaAtributos"));
				}
			}

			if (entidad.equalsIgnoreCase("servFindOrdenBienvenida")) {
				respuestaEntidad = serviciosBasicoFacade.servFindOrdenBienvenida();

			}

			if (entidad.equalsIgnoreCase("setCtxPruebas")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					StringTokenizer flujo = new StringTokenizer(request.getParameter("cadenaAtributos"), ";");
					session.setAttribute("idPrueba", flujo.nextToken());
					session.setAttribute("tipoPrueba", flujo.nextToken());
					session.setAttribute("nroPlaca", flujo.nextToken());
					session.setAttribute("nroTurno", flujo.nextToken());
					session.setAttribute("condicion", flujo.nextToken());
					session.setAttribute("tipo", flujo.nextToken());
					session.setAttribute("servicio", flujo.nextToken());
					session.setAttribute("ctxHojaPrueba", flujo.nextToken());
					session.setAttribute("ctxTipoVehiculo", flujo.nextToken());
					session.setAttribute("ctxNroEjes", flujo.nextToken());
					session.setAttribute("ctxEnsenanza", flujo.nextToken());
					session.setAttribute("ctxPoseePacha", flujo.nextToken());
					session.setAttribute("ctxNacionalidad", flujo.nextToken());

				}
			}

			if (entidad.equalsIgnoreCase("getCtxPruebas")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = (String) session.getAttribute("nroPlaca");
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("idPrueba"));
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("tipoPrueba"));
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("nroTurno"));
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("condicion"));
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("tipo"));
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("servicio"));
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("ctxHojaPrueba"));
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("ctxTipoVehiculo"));
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("ctxNroEjes"));
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("ctxEnsenanza"));
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("ctxPoseePacha"));
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("ctxNacionalidad"));
				}
			}

			if (entidad.equalsIgnoreCase("servFindDatosTakeRunt")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servFindDatosRecepcion(((String)session.getAttribute("CertPreRevision")));
					respuestaEntidad = respuestaEntidad.concat(";").concat((String)session.getAttribute("CertPreRevision"));
				}
			}

			if (entidad.equalsIgnoreCase("servFindVehiculo")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servFindVehiculo(request.getParameter("cadenaAtributos"));
				}
			}
			if (entidad.equalsIgnoreCase("servFindDatosTakeSoat")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servFindDatosSoat((String)session.getAttribute("CertPreRevision"));
					respuestaEntidad = respuestaEntidad.concat(";").concat((String)session.getAttribute("CertPreRevision"));
				}
			}

			if (entidad.equalsIgnoreCase("serFindPropietario")) {
				respuestaEntidad = serviciosBasicoFacade.servFindDatosPropietario(request.getParameter("cadenaAtributos").trim());
			}

			if (entidad.equalsIgnoreCase("serFindUbicPropietario")) {
				HttpSession session = request.getSession(false);
				respuestaEntidad = serviciosBasicoFacade.servFindDatosUbicacionConductor((String)session.getAttribute("CertPreRevision"));
			}



			if (entidad.equalsIgnoreCase("servConfirmDatosRunt")) {
				respuestaEntidad = serviciosBasicoFacade.servConfirmDatosRunt(request.getParameter("cadenaAtributos"));
			}

			if (entidad.equalsIgnoreCase("servFindFuncConsola")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servFindFuncConsola(request.getParameter("cadenaAtributos"));
				}
			}
			if (entidad.equalsIgnoreCase("funcCmbEdoSicov")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servFuncCmbEdoSicov(request.getParameter("cadenaAtributos"));
				}
			}
			if (entidad.equalsIgnoreCase("funcCambioPlaca")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servFuncCambioPlaca(request.getParameter("cadenaAtributos"));
				}
			}
			if (entidad.equalsIgnoreCase("funcAnularRTM")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servFuncAnularRTM(request.getParameter("cadenaAtributos"));
				}
			}
			if (entidad.equalsIgnoreCase("funcCorregirPin")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servFuncCorregirPin(request.getParameter("cadenaAtributos"));
				}
			}


			if (entidad.equalsIgnoreCase("funcCmbTipoRevision")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = serviciosBasicoFacade.servFuncCmbTipoRevision(request.getParameter("cadenaAtributos"));
				}
			}

			if (entidad.equalsIgnoreCase("servConfirmDatosSoat")) {
				respuestaEntidad = serviciosBasicoFacade.servConfirmDatosSoat(request.getParameter("cadenaAtributos"));
			}
			if (entidad.equalsIgnoreCase("servFindDatosConductor")) {
				HttpSession session = request.getSession(false);
				respuestaEntidad = serviciosBasicoFacade.servFindDatosConductor((String)session.getAttribute("CertPreRevision"));

			}

			if (entidad.equalsIgnoreCase("servActualizacionDatosConductor")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					String atrrStreaming = (String)session.getAttribute("CertPreRevision");
					atrrStreaming =atrrStreaming.concat(";").concat(request.getParameter("cadenaAtributos")).concat(";").concat((String) session.getAttribute("identificador"));
					respuestaEntidad = serviciosBasicoFacade.servActualizarDatosConductor(atrrStreaming);
				}
			}

			if (entidad.equalsIgnoreCase("servAutorizacionPruebas")) {
				HttpSession session = request.getSession(false);
				String atrrStreaming = (String)session.getAttribute("CertPreRevision");
				atrrStreaming =atrrStreaming.concat(";").concat(request.getParameter("cadenaAtributos"));
				respuestaEntidad = serviciosBasicoFacade.servAutorizacionPreRevision(atrrStreaming);

			}
			if (entidad.equalsIgnoreCase("findAvatar")) {
				HttpSession session = request.getSession(false);
				String ed = File.separator.concat("opt").concat(File.separator).concat("SART-WEB-2.0").concat(File.separator).concat("Avatar").concat(File.separator).concat((String) session.getAttribute("cedula")).concat(".png");
				File f = new File(File.separator.concat("opt").concat(File.separator).concat("SART-WEB-2.0").concat(File.separator).concat("Avatar").concat(File.separator).concat((String) session.getAttribute("cedula")).concat(".png"));

				FileInputStream fis = new FileInputStream(f);
				response.setContentLength(fis.available());
				BufferedImage bi = ImageIO.read(fis);
				OutputStream os = response.getOutputStream();
				ImageIO.write(bi, "png", os);
				os.flush();
			}

			if (entidad.equalsIgnoreCase("closeSession")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "0";
				} else {
					session.invalidate();
					respuestaEntidad = "1";
				}
			}
			if (entidad.equalsIgnoreCase("servFindClasesVehiculoAct")) {
				respuestaEntidad = serviciosBasicoFacade.servFindClasesVehiculoAct();
			}
			if (entidad.equalsIgnoreCase("existenciaSession")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "0";
				} else {
					respuestaEntidad = (String) session.getAttribute("cedula");
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("nombreUsuario"));
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("nickUsuario"));
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("identificador"));
					respuestaEntidad = respuestaEntidad.concat(";Y");
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("rolUsuario"));
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("instancia"));
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("fkRevision"));

				}
			}
			if (entidad.equalsIgnoreCase("restoredIdentidad")) {
				HttpSession session = request.getSession();
				String eves=(String)session.getAttribute("nivel");
				respuestaEntidad = (String) session.getAttribute("cedula");
				respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("nickUsuario"));
				respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("identificador"));
				respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("nivel"));
			}

			if (entidad.equalsIgnoreCase("findCtxPreRevisiones")) {
				respuestaEntidad = serviciosBasicoFacade.servFindCtxPreRevision(request.getParameter("cadenaAtributos"));
			}

			if (entidad.equalsIgnoreCase("preIngreso")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = (String) session.getAttribute("NoTurno");
				}
			}
			if (entidad.equalsIgnoreCase("regManualRunt")) {
				HttpSession session = request.getSession();
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					String cadenaAtributo = ((String) session.getAttribute("identificador")).concat(";").concat(request.getParameter("cadenaAtributos"));
					respuestaEntidad = serviciosBasicoFacade.servRegManualRunt(cadenaAtributo);
					StringTokenizer flujo = new StringTokenizer(respuestaEntidad, ";");
					session.setAttribute("fkRevision", flujo.nextToken());
					session.setAttribute("placa", flujo.nextToken());
					session.setAttribute("tipoCombustible", flujo.nextToken());
					session.setAttribute("tipoVehiculo", flujo.nextToken());
					session.setAttribute("nroEjes", flujo.nextToken());
					session.setAttribute("servicio", flujo.nextToken());
					session.setAttribute("cedulaPoseedor", flujo.nextToken());
					session.setAttribute("pesoBruto", flujo.nextToken());
					session.setAttribute("tipoIdentificacion",flujo.nextToken());
					session.setAttribute("nacionalidad",flujo.nextToken());
					Calendar fecha = Calendar.getInstance();
					Date fec = fecha.getTime();
					session.setAttribute("inicProceso",String.valueOf(fec.getTime()));
				}
			}


			if (entidad.equalsIgnoreCase("setNaturalezaPreRevision")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {

					session.removeAttribute("placa");
					session.removeAttribute("tipoVehiculo");
					session.removeAttribute("nroEjes");
					session.removeAttribute("fkRevision");
					session.removeAttribute("servicio");
					session.removeAttribute("tipoCombustible");
					session.removeAttribute("cedulaPoseedor");
					session.removeAttribute("pesoBruto");
					session.removeAttribute("inicProceso");
					session.removeAttribute("cedulaPoseedor");
					session.removeAttribute("nombreCompleto");
					session.removeAttribute("tipoIdentificacion");
					session.removeAttribute("nacionalidad");
					session.removeAttribute("nroSillas");
				}
			}
			if (entidad.equalsIgnoreCase("findCtxPreRevision")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = ((String) session.getAttribute("ctxRevision")).concat(";").concat(((String) session.getAttribute("nroFactura")));
					respuestaEntidad = respuestaEntidad.replaceAll("\"", "");
				}
			}
			if (entidad.equalsIgnoreCase("getNaturalezaPreRevision")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					Calendar fecha = Calendar.getInstance();
					Date fec = fecha.getTime();
					context.setAttribute("inicProceso",String.valueOf(fec.getTime()));
					respuestaEntidad = serviciosBasicoFacade.servFindExisRevision(request.getParameter("cadenaAtributos"));
				}
			}
			if (entidad.equalsIgnoreCase("transmisionDatosRunt")) {
				String reqAtrr =request.getParameter("cadenaAtributos");
				respuestaEntidad = serviciosBasicoFacade.servRegAutomaticoRunt(reqAtrr);
				StringTokenizer flujo = new StringTokenizer(respuestaEntidad, ";");
				context.setAttribute("fkRevision", flujo.nextToken());
				context.setAttribute("placa", flujo.nextToken());
				context.setAttribute("tipoCombustible", flujo.nextToken());
				context.setAttribute("tipoVehiculo", flujo.nextToken());
				context.setAttribute("nroEjes", flujo.nextToken());
				context.setAttribute("servicio", flujo.nextToken());
				context.setAttribute("cedulaPoseedor", flujo.nextToken());
				String eve = flujo.nextToken();
				context.setAttribute("pesoBruto", eve);
				context.setAttribute("tipoIdentificacion", flujo.nextToken());
				context.setAttribute("nroSillas", flujo.nextToken());
			}
			if (entidad.equalsIgnoreCase("takeCtxConductor")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = (String) session.getAttribute("cedulaPoseedor");
					String nombreCompleto =(String) session.getAttribute("nombreCompleto");
					if( nombreCompleto==null) {
						session.setAttribute("nombreCompleto"," ");
					}
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("nombreCompleto")) ;
				}
			}

			if (entidad.equalsIgnoreCase("autorizacionPreRevision")) {
				String strAtrr = request.getParameter("cadenaAtributos");
				respuestaEntidad = serviciosBasicoFacade.servAutorizacionPreRevision(request.getParameter("cadenaAtributos"));
				HttpSession session = request.getSession(false);
				if (session!= null) {
					StringTokenizer flujo = new StringTokenizer(respuestaEntidad, ";");
					session.setAttribute("cedulaPoseedor", flujo.nextToken());
					session.setAttribute("nombreCompleto",flujo.nextToken().concat(";").concat(flujo.nextToken()));
				}
			}
			if (entidad.equalsIgnoreCase("registroPSI")) {
				HttpSession session = request.getSession(false);
				respuestaEntidad = serviciosBasicoFacade.servRegistroPSI((String)session.getAttribute("fkRevision"),request.getParameter("cadenaAtributos"));
			}

			if (entidad.equalsIgnoreCase("setCtxPreRevision")) {
				HttpSession session = request.getSession(false);
				if (session!= null) {
					String ctxPlaca = (String) session.getAttribute("placa");
					if(ctxPlaca==null ) {
						session.setAttribute("placa", (String) context.getAttribute("placa"));
						session.setAttribute("tipoVehiculo",  (String) context.getAttribute("tipoVehiculo"));
						session.setAttribute("nroEjes",(String) context.getAttribute("nroEjes"));
						session.setAttribute("fkRevision",(String) context.getAttribute("fkRevision"));
						session.setAttribute("servicio",(String) context.getAttribute("servicio"));
						session.setAttribute("tipoCombustible", (String) context.getAttribute("tipoCombustible"));
						session.setAttribute("cedulaPoseedor", (String) context.getAttribute("cedulaPoseedor"));
						session.setAttribute("pesoBruto", (String) context.getAttribute("pesoBruto"));
						session.setAttribute("nroSillas", (String) context.getAttribute("nroSillas"));
						session.setAttribute("inicProceso", (String) context.getAttribute("inicProceso"));
						session.setAttribute("tipoIdentificacion", (String) context.getAttribute("tipoIdentificacion"));
						session.setAttribute("nacionalidad","N");
						context.removeAttribute("fkRevision");
						context.removeAttribute("placa");
						context.removeAttribute("tipoCombustible");
						context.removeAttribute("servicio");
						context.removeAttribute("nroEjes");
						context.removeAttribute("claseVehiculo");
						context.removeAttribute("cedulaPoseedor");
						context.removeAttribute("pesoBruto");
						context.removeAttribute("inicProceso");
						context.removeAttribute("tipoIdentificacion");
						context.removeAttribute("nroSillas");
					}
					respuestaEntidad ="1";
				}else {
					respuestaEntidad ="0";
				}
			}

			if (entidad.equalsIgnoreCase("takeCtxPreRevision")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					respuestaEntidad = (String) session.getAttribute("placa");
					try {
						String nroEje =(String) session.getAttribute("nroEjes");
						if(nroEje!=null ) {

							respuestaEntidad = respuestaEntidad.concat(";").concat(nroEje) ;
						}else {
							session.setAttribute("nroEjes","2");
							respuestaEntidad = respuestaEntidad.concat(";").concat("2") ;
						}
					}catch(Exception ex) {
						session.setAttribute("nroEjes","2");
						respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("nroEjes")) ;
					}
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("servicio")) ;
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("tipoVehiculo")) ;
					Integer aprobado = (Integer) session.getAttribute("aprobado");
					if(aprobado !=null) {
						respuestaEntidad = respuestaEntidad.concat(";").concat(String.valueOf((Integer) session.getAttribute("aprobado")));
					}else {
						respuestaEntidad = respuestaEntidad.concat(";0");
					}
					respuestaEntidad =respuestaEntidad.concat(";").concat((String) session.getAttribute("fkRevision")) ;
					respuestaEntidad =respuestaEntidad.concat(";").concat((String) session.getAttribute("cedulaPoseedor")) ;
					String pesoBruto = (String) session.getAttribute("pesoBruto");
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("pesoBruto")) ;
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("tipoIdentificacion")) ;
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("tipoCombustible")) ;
					if(session.getAttribute("nroSillas")== null) {
						session.setAttribute("nroSillas","2");
					}
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("nroSillas")) ;
					respuestaEntidad = respuestaEntidad.concat(";").concat((String) session.getAttribute("nacionalidad")) ;
					String ctxFuntion = request.getParameter("cadenaAtributos");
					if(ctxFuntion.length()>3) {
						String responseEntidad=  serviciosBasicoFacade.servFindCtxPreRevision(request.getParameter("cadenaAtributos"));
						respuestaEntidad = respuestaEntidad.concat(responseEntidad);
					}
				}
			}

			if (entidad.equalsIgnoreCase("servFindOrdenPreRevision")) {
				respuestaEntidad = serviciosBasicoFacade.servFindOrdenPreRevision(request.getParameter("cadenaAtributos"));
			}

			if (entidad.equalsIgnoreCase("findExistCedulaUser")) {
				respuestaEntidad = serviciosBasicoFacade.servFindUsuario(request.getParameter("cadenaAtributos"));
			}
			if (entidad.equalsIgnoreCase("setAnulacionPreRevision")) {
				respuestaEntidad = serviciosBasicoFacade.setAnulacionPreRevision(Integer.parseInt(request.getParameter("cadenaAtributos")));

			}
			if (entidad.equalsIgnoreCase("servicioRechazo")) {
				HttpSession session = request.getSession(false);
				String strRechazo = request.getParameter("cadenaAtributos").concat(";").concat((String) session.getAttribute("identificador"));
				respuestaEntidad = serviciosBasicoFacade.servPersistRechazoVehiculo(strRechazo);
			}
			if (entidad.equalsIgnoreCase("inspeccionPreRevision")) {
				HttpSession session = request.getSession(false);
				if (session == null) {
					respuestaEntidad = "2";
				} else {
					String strPreRevision = request.getParameter("cadenaAtributos").concat("%").concat((String) session.getAttribute("identificador")).concat(";").concat((String) session.getAttribute("inicProceso"));
					respuestaEntidad = serviciosBasicoFacade.servInspeccionPreRevision(strPreRevision,(String) session.getAttribute("fkRevision"));
					StringTokenizer flujo = new StringTokenizer(respuestaEntidad, ";");
					if (flujo.countTokens() == 1) {
						respuestaEntidad = "1";
						session.setAttribute("aprobado", 0);
					} else {
						session.setAttribute("NoTurno", flujo.nextToken());
						respuestaEntidad = "x";
						session.setAttribute("aprobado", 1);
					}
				}

			}
			if (entidad.equalsIgnoreCase("PreIngreso2Fase")) {
				HttpSession session = request.getSession(false);
				String strPreRevision =request.getParameter("cadenaAtributos").concat("%").concat((String) session.getAttribute("fkRevision"));

				respuestaEntidad = serviciosBasicoFacade.servPersistPreRevision2Fase(strPreRevision);
			}

			if (entidad.equalsIgnoreCase("servRegistroInventario")) {
				HttpSession session = request.getSession(false);
				String strPreReVehicular =request.getParameter("cadenaAtributos").concat("%").concat((String) session.getAttribute("fkRevision"));
				String eve = strPreReVehicular;
				respuestaEntidad = serviciosBasicoFacade.servRegistroInventario(strPreReVehicular);
			}

		} finally {
			out.print(respuestaEntidad);
			out.close();

		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
	// + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>



} 