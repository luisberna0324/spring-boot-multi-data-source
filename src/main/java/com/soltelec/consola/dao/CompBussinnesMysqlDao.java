package com.soltelec.consola.dao;


import com.soltelec.consola.model.*;
import com.soltelec.consola.model.Color;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.List;
import java.util.*;


public class CompBussinnesMysqlDao implements CompBussinnesMysql {
    String respImplDao;
    private Query queryDao;

    @Override
    public String servBuiltGrafoHP(Integer id, EntityManager emMysql, EntityManager em) {
        PreRevision preRevision = em.find(PreRevision.class, id);
        Recepcion recepcion = em.find(Recepcion.class, preRevision.getFkRecepcion());
        Cda cda = emMysql.find(Cda.class, 1);
        Ciudad ciudad = emMysql.find(Ciudad.class, Long.parseLong(recepcion.getIdCiudad().toString()));
        Ciudad ciudadCda = emMysql.find(Ciudad.class, Long.parseLong(cda.getCodigoCiudad().toString()));
        UsuarioMySql usr = servFindUsuario(recepcion.getUsuario(), emMysql);
        Vehiculo vehiculo = serFindVehiculo(recepcion.getPlaca(), emMysql);
        vehiculo.setPlaca(recepcion.getPlaca());
        vehiculo.setModelo(Integer.parseInt(recepcion.getModelo()));
        vehiculo.setCapacidadCarga(Integer.parseInt(recepcion.getCapacidaCarga().trim()));
        vehiculo.setCarroceria(recepcion.getCarroceria());
        vehiculo.setNroSerie(recepcion.getNroSerie());
        if (recepcion.getEsScooter() != null) {
            if (recepcion.getEsScooter() == true) {
                vehiculo.setDiseno(Diseno.Scooter);
            } else {
                vehiculo.setDiseno(Diseno.Convencional);
            }
        }
        if (vehiculo.getVelocidad() == 1) {
            vehiculo.setDiametro(0);
            vehiculo.setTiemposMotor(4);

        }
        if (preRevision.getBlindado() == true) {
            vehiculo.setBlindaje("Y");
        } else {
            vehiculo.setBlindaje("N");
        }
        if (preRevision.getEsEnsenanza() == true) {
            vehiculo.setEsEnsenaza(1);
        } else {
            vehiculo.setEsEnsenaza(0);
        }
        if (preRevision.getvidriosPolarizados() == true) {
            vehiculo.setVidriosPolarizados("Y");
        } else {
            vehiculo.setVidriosPolarizados("N");
        }
        vehiculo.setModelo(Integer.parseInt(recepcion.getModelo()));
        vehiculo.setCilindraje(Integer.parseInt(recepcion.getCilindrada()));
        vehiculo.setLicencia(recepcion.getNroLicencia());
        vehiculo.setEjes(Integer.parseInt(recepcion.getNroEjes().trim()));

        int tipoVeh = 0;
        int catVeh = 0;
        if (recepcion.getClaseVehiculo().equalsIgnoreCase("AUTOMOVIL") || recepcion.getClaseVehiculo().equalsIgnoreCase("CAMIONETA") || recepcion.getClaseVehiculo().equalsIgnoreCase("CAMPERO")) {
            catVeh = 10;
        }
        if (recepcion.getClaseVehiculo().equalsIgnoreCase("MOTOCICLETA")) {
            catVeh = 4;
        }
        if (recepcion.getClaseVehiculo().equalsIgnoreCase("CAMION") || recepcion.getClaseVehiculo().equalsIgnoreCase("TRACTOCAMION") || recepcion.getClaseVehiculo().equalsIgnoreCase("VOLQUETA")) {
            catVeh = 10;
        }
        if (recepcion.getClaseVehiculo().equalsIgnoreCase("MOTOCARRO")) {
            catVeh = 5;
        }
        if (recepcion.getClaseVehiculo().equalsIgnoreCase("BUSETA")) {
            catVeh = 10;
        }
        if (recepcion.getClaseVehiculo().equalsIgnoreCase("MICROBUS")) {
            catVeh = 10;
        }
        if (recepcion.getClaseVehiculo().equalsIgnoreCase("BUS")) {
            catVeh = 10;
        }
        if (recepcion.getPoseePacha() != null) {
            if (recepcion.getPoseePacha() == true) {
                preRevision.setTipoVehiculo(3);
            }
        }
        TipoVehiculo tipoVehiculo = emMysql.find(TipoVehiculo.class, preRevision.getTipoVehiculo());
        vehiculo.setTipoVehiculo(tipoVehiculo);
        vehiculo.setFecha(recepcion.getFechaMatricula());
        vehiculo.setUsuario(usr.getUsuario());
        vehiculo.setCategoria(catVeh);
        vehiculo.setNacionalidad(preRevision.getNacionalidad());
        Pais pais = null;
        if (preRevision.getNacionalidad().equalsIgnoreCase("E")) {
            pais = emMysql.find(Pais.class, 465);
        } else {
            pais = emMysql.find(Pais.class, 90);
        }
        vehiculo.setPais(pais);
        Propietario propietario = emMysql.find(Propietario.class, Long.parseLong(recepcion.getCedula().trim()));
        if (propietario != null) {

            vehiculo.setPropietario(propietario);
            propietario.setLicencia(recepcion.getNroLicencia());
            propietario.setNombres(recepcion.getNombres().toUpperCase());
            propietario.setApellidos(recepcion.getApellidos().toUpperCase());
            propietario.setFechaRegistro(recepcion.getFechaMatricula());
            propietario.setUsuario(recepcion.getUsuario());
            propietario.setDireccion(recepcion.getDireccionConduc());
            propietario.setTelefono(recepcion.getTelefono());
            propietario.setCelular(recepcion.getTelefono());
            propietario.setEmail(recepcion.getCorreo());
            if (recepcion.getCiudadania().equalsIgnoreCase("C")) {
                propietario.setTipoIdentificacion(TipoIdentificacion.CC);
            }

            if (recepcion.getCiudadania().equalsIgnoreCase("CC")) {
                propietario.setTipoIdentificacion(TipoIdentificacion.CC);
            }
            propietario.setTipolicencia("C1");
            propietario.setCiudad(ciudad);
            emMysql.merge(propietario);
        } else {
            propietario = new Propietario();
            propietario.setId(Long.parseLong(recepcion.getCedula().trim()));
            propietario.setLicencia(recepcion.getNroLicencia());
            propietario.setNombres(recepcion.getNombres().toUpperCase());
            propietario.setApellidos(recepcion.getApellidos().toUpperCase());
            propietario.setFechaRegistro(recepcion.getFechaMatricula());
            propietario.setUsuario(recepcion.getUsuario());
            propietario.setDireccion(recepcion.getDireccionConduc());
            propietario.setTelefono(recepcion.getTelefono());
            propietario.setCelular(recepcion.getTelefono());
            propietario.setEmail(recepcion.getCorreo());
            List<Vehiculo> lstVehiculo = new ArrayList();
            lstVehiculo.add(vehiculo);
            propietario.setVehiculoList(lstVehiculo);
            if (recepcion.getCiudadania().equalsIgnoreCase("C")) {
                propietario.setTipoIdentificacion(TipoIdentificacion.CC);
            }

            if (recepcion.getCiudadania().equalsIgnoreCase("CC")) {
                propietario.setTipoIdentificacion(TipoIdentificacion.CC);
            }

            if (recepcion.getCiudadania().equalsIgnoreCase("N")) {
                propietario.setTipoIdentificacion(TipoIdentificacion.NIT);
            }
            propietario.setTipolicencia("C1");
            propietario.setCiudad(ciudad);
            vehiculo.setPropietario(propietario);
        }
        Propietario conductor;
        if (recepcion.getCedulaConduc().trim().equalsIgnoreCase(recepcion.getCedula().trim())) {
            conductor = propietario;
        } else {
            conductor = emMysql.find(Propietario.class, Long.parseLong(recepcion.getCedulaConduc().trim()));
            if (conductor != null) {
                conductor.setLicencia(recepcion.getNroLicencia());
                conductor.setNombres(recepcion.getNombresConduc().toUpperCase());
                conductor.setApellidos(recepcion.getApellidosConduc().toUpperCase());
                conductor.setFechaRegistro(recepcion.getFechaMatricula());
                conductor.setUsuario(recepcion.getUsuario());
                conductor.setDireccion(recepcion.getDireccionConduc());
                conductor.setTelefono(recepcion.getTelefono());
                conductor.setCelular(recepcion.getTelefono());
                conductor.setEmail(recepcion.getCorreo());
                if (recepcion.getCiudadaniaConduc().equalsIgnoreCase("C")) {
                    conductor.setTipoIdentificacion(TipoIdentificacion.CC);
                }
                if (recepcion.getCiudadaniaConduc().equalsIgnoreCase("CC")) {
                    conductor.setTipoIdentificacion(TipoIdentificacion.CC);
                }
                if (recepcion.getCiudadaniaConduc().equalsIgnoreCase("N")) {
                    conductor.setTipoIdentificacion(TipoIdentificacion.NIT);
                }
                conductor.setTipolicencia("C1");
                conductor.setCiudad(ciudad);
                emMysql.merge(conductor);
            } else {
                conductor = new Propietario();
                conductor.setId(Long.parseLong(recepcion.getCedulaConduc().trim()));
                conductor.setLicencia(recepcion.getNroLicencia());
                conductor.setNombres(recepcion.getNombresConduc().toUpperCase());
                conductor.setApellidos(recepcion.getApellidosConduc().toUpperCase());
                conductor.setFechaRegistro(recepcion.getFechaMatricula());
                conductor.setUsuario(recepcion.getUsuario());
                conductor.setDireccion(recepcion.getDireccionConduc());
                conductor.setTelefono(recepcion.getTelefono());
                conductor.setEmail(recepcion.getCorreo());
                if (recepcion.getCiudadaniaConduc().equalsIgnoreCase("C")) {
                    conductor.setTipoIdentificacion(TipoIdentificacion.CC);
                }
                if (recepcion.getCiudadaniaConduc().equalsIgnoreCase("CC")) {
                    conductor.setTipoIdentificacion(TipoIdentificacion.CC);
                }
                if (recepcion.getCiudadaniaConduc().equalsIgnoreCase("N")) {
                    conductor.setTipoIdentificacion(TipoIdentificacion.NIT);
                }
                conductor.setTipolicencia("C1");
                conductor.setCiudad(ciudad);
            }
        }


        vehiculo.setExostos(preRevision.getNoExosto());
        int tipoGas = 0;
        if (recepcion.getCombustible().equalsIgnoreCase("GASOLINA")) {
            tipoGas = 1;
        }
        if (recepcion.getCombustible().equalsIgnoreCase("DIESEL")) {
            tipoGas = 3;
        }
        if (recepcion.getCombustible().equalsIgnoreCase("GAS-GASOLINA")) {
            tipoGas = 4;
        }
        if (recepcion.getCombustible().equalsIgnoreCase("GAS NATURAL VEHICULAR")) {
            tipoGas = 2;
        }
        if (recepcion.getCombustible().equalsIgnoreCase("GPL")) {
            tipoGas = 9;
        }

        TipoGasolina tipoGasolina = emMysql.find(TipoGasolina.class, tipoGas);
        vehiculo.setTipoGasolina(tipoGasolina);

        vehiculo.setNumeroSOAT(recepcion.getNroPolizaSoat());
        vehiculo.setFechaExpedicionSOAT(recepcion.getFechaExpedicionSoat());
        vehiculo.setFechaSOAT(recepcion.getFechaFinExpedicionSoat());
        ServicioEspecial servicioEspecial = emMysql.find(ServicioEspecial.class, 1);
        vehiculo.setServicioEspecial(servicioEspecial);
        vehiculo.setMotor(recepcion.getNroMotor());
        vehiculo.setVin(recepcion.getNroVin());
        vehiculo.setFechaRegistro(recepcion.getFechaMatricula());
        vehiculo.setKilometraje(preRevision.getKilometraje());
        vehiculo.setSillas(Integer.parseInt(recepcion.getNroSillas().trim()));
        vehiculo.setPesoBruto(Integer.parseInt(recepcion.getPesoBruto().trim()));
        vehiculo.setChasis(recepcion.getNroChasis());
        Marca marca = serFindMarca(recepcion.getMarca(), emMysql);
        vehiculo.setMarca(marca);
        int tipoClase = 0;
        if (recepcion.getClaseVehiculo().equalsIgnoreCase("AUTOMOVIL")) {
            tipoClase = 1;
        }
        if (recepcion.getClaseVehiculo().equalsIgnoreCase("CAMIONETA")) {
            tipoClase = 5;
        }
        if (recepcion.getClaseVehiculo().equalsIgnoreCase("MOTOCICLETA")) {
            tipoClase = 10;
        }
        if (recepcion.getClaseVehiculo().equalsIgnoreCase("CAMION")) {
            tipoClase = 4;
        }

        if (recepcion.getClaseVehiculo().equalsIgnoreCase("TRACTOCAMION")) {
            tipoClase = 8;
        }
        if (recepcion.getClaseVehiculo().equalsIgnoreCase("MOTOCARRO")) {
            tipoClase = 14;
        }
        if (recepcion.getClaseVehiculo().equalsIgnoreCase("VOLQUETA")) {
            tipoClase = 42;
        }

        if (recepcion.getClaseVehiculo().equalsIgnoreCase("BUSETA")) {
            tipoClase = 3;
        }
        if (recepcion.getClaseVehiculo().equalsIgnoreCase("MICROBUS")) {
            tipoClase = 7;
        }
        if (recepcion.getClaseVehiculo().equalsIgnoreCase("BUS")) {
            tipoClase = 2;
        }
        if (recepcion.getClaseVehiculo().equalsIgnoreCase("CAMPERO")) {
            tipoClase = 6;
        }
        ClaseVehiculo claseVehiculo = emMysql.find(ClaseVehiculo.class, tipoClase);
        vehiculo.setClaseVehiculo(claseVehiculo);
        LineaVehiculo lineaVehiculo = serFindLineasVehiculo(recepcion.getLinea(), emMysql, marca);
        vehiculo.setLineaVehiculo(lineaVehiculo);
        Color color = serFindColor(recepcion.getColor(), emMysql);
        vehiculo.setColor(color);
        Aseguradora aseguradora = serFindAseguradora(recepcion.getEntidadSoat(), emMysql);
        vehiculo.setAseguradora(aseguradora);
        Llanta llanta = serFindLlanta(preRevision.getNoLLanta().trim(), emMysql);
        vehiculo.setLlantas(llanta);
        int tipoServicio = 3;
        if (recepcion.getServicio().equalsIgnoreCase("Publico")) {
            tipoServicio = 2;
        }
        Servicio servicio = emMysql.find(Servicio.class, tipoServicio);
        vehiculo.setServicios(servicio);
        Prueba prueba;
        HojaPruebas hojaPruebas;
        List<Prueba> lstPruebasAut = new ArrayList();
        if (preRevision.getPrimeraVez() == 1) {
            hojaPruebas = new HojaPruebas();
            hojaPruebas.setId(servFindMaxIdHP(emMysql));
            hojaPruebas.setNroTurno(preRevision.getNoTurno());
            hojaPruebas.setConsecutivoRunt(String.valueOf("0"));
            hojaPruebas.setPreRevision(preRevision.getNoRevision());
            if (recepcion.getCedulaConduc().equalsIgnoreCase(recepcion.getCedula())) {
                hojaPruebas.setConductor(propietario);
            } else {
                hojaPruebas.setConductor(conductor);
            }

            hojaPruebas.setVehiculo(vehiculo);
            hojaPruebas.setPropietario(propietario);
            hojaPruebas.setUsuario(usr.getUsuario());
            Integer p = cda.getResponsable();
            UsuarioMySql usrResp = servFindUsuario(cda.getResponsable(), emMysql);
            hojaPruebas.setResponsable(usrResp);
            hojaPruebas.setFinalizada("N");
            hojaPruebas.setActiva("Y");
            hojaPruebas.setFechaIngreso(preRevision.getFechaRevision());
            hojaPruebas.setAnulado("N");
            hojaPruebas.setAprobado("N");
            hojaPruebas.setFechaExpSoat(recepcion.getFechaInicExpedicionSoat());
            hojaPruebas.setFechaVencSoat(recepcion.getFechaFinExpedicionSoat());
            hojaPruebas.setNroIdentificacionSoat(recepcion.getNroPolizaSoat());
            hojaPruebas.setAseguradora(aseguradora);
            hojaPruebas.setUbicacionMunicipio(ciudadCda.getCiudadPrincipal());
            hojaPruebas.setIntentos(1);
            char temp = "I".charAt(0);
            hojaPruebas.setFormaMedTemperatura(temp);
            hojaPruebas.setNroPruebasRegistradas(0);
            if (preRevision.getEsRTM() == true) {
                hojaPruebas.setPreventiva("N");
                if (cda.getProveedorSicov().equalsIgnoreCase("CI2")) {
                    hojaPruebas.setEstadoSICOV("Registrada");
                } else {
                    hojaPruebas.setEstadoSICOV("INICIADO");
                }
            } else {
                hojaPruebas.setPreventiva("Y");
                hojaPruebas.setEstadoSICOV("NO_APLICA");
            }
            hojaPruebas = servFindConsecutivoHoja(hojaPruebas, emMysql);

            for (int i = 1; i < 9; i++) {
                if (i == 4 && recepcion.getClaseVehiculo().equalsIgnoreCase("MOTOCICLETA")) {
                    i++;
                }
                if (i == 6 && (recepcion.getClaseVehiculo().equalsIgnoreCase("MOTOCICLETA") || recepcion.getClaseVehiculo().equalsIgnoreCase("CAMION") || recepcion.getClaseVehiculo().equalsIgnoreCase("BUSETA") || recepcion.getClaseVehiculo().equalsIgnoreCase("BUS") || recepcion.getClaseVehiculo().equalsIgnoreCase("TRACTOCAMION") || recepcion.getClaseVehiculo().equalsIgnoreCase("VOLQUETA") || recepcion.getClaseVehiculo().equalsIgnoreCase("REMOLQUE") || recepcion.getClaseVehiculo().trim().equalsIgnoreCase("MICROBUS"))) {
                    i++;
                }
                prueba = new Prueba();
                prueba.setFecha(preRevision.getFechaRevision());
                prueba.setFechaFinal(preRevision.getFechaRevision());
                TipoPrueba tipoPrueba = emMysql.find(TipoPrueba.class, i);
                prueba.setTipoPrueba(tipoPrueba);
                prueba.setHojaPruebas(hojaPruebas);
                prueba.setUsuarioFor(usr);
                prueba.setAbortado("N");
                prueba.setFinalizada("N");
                prueba.setAprobado("N");
                lstPruebasAut.add(prueba);
            }
            if (preRevision.getTipoVehiculo() == 110) {
                prueba = new Prueba();
                prueba.setFecha(preRevision.getFechaRevision());
                prueba.setFechaFinal(preRevision.getFechaRevision());
                TipoPrueba tipoPrueba = emMysql.find(TipoPrueba.class, 9);
                prueba.setTipoPrueba(tipoPrueba);
                prueba.setHojaPruebas(hojaPruebas);
                prueba.setUsuarioFor(usr);
                prueba.setAbortado("N");
                prueba.setFinalizada("N");
                prueba.setAprobado("N");
                lstPruebasAut.add(prueba);
            }
            hojaPruebas.setListPruebas(lstPruebasAut);
            emMysql.persist(hojaPruebas);
        } else {
            Calendar calenInic = Calendar.getInstance();
            Calendar calenFin = Calendar.getInstance();
            LocalDate date = calenInic.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            TemporalUnit unidadTemporal = ChronoUnit.DAYS;
            LocalDate dateResultado = date.minus(15, unidadTemporal);
            calenInic.setTime(Date.from(dateResultado.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            queryDao = emMysql.createNamedQuery("HojaPruebas.findTestSheet");
            queryDao.setParameter("placa", recepcion.getPlaca().trim());
            queryDao.setParameter("fechaInicial", calenInic.getTime());
            queryDao.setParameter("fechaFinal", calenFin.getTime());
            hojaPruebas = (HojaPruebas) queryDao.getResultList().iterator().next();
            hojaPruebas.setConductor(conductor);
            hojaPruebas.setPropietario(propietario);
            hojaPruebas.setNroPruebasRegistradas(0);
            hojaPruebas.setIntentos(2);
            hojaPruebas.setNroTurno(preRevision.getNoTurno());
            if (preRevision.getEsRTM() == true) {
                if (cda.getProveedorSicov().equalsIgnoreCase("CI2")) {
                    hojaPruebas.setEstadoSICOV("Registrada");
                } else {
                    hojaPruebas.setEstadoSICOV("INICIADO");
                }

            }
            hojaPruebas.setPreRevision(preRevision.getNoRevision());
            hojaPruebas.setFinalizada("N");
            Boolean doView = true;
            for (Prueba pr : hojaPruebas.getListPruebas()) {
                if (pr.getAprobado().equalsIgnoreCase("N") && pr.getFinalizada().equalsIgnoreCase("Y")) {
                    prueba = new Prueba();
                    prueba.setFecha(preRevision.getFechaRevision());
                    prueba.setFechaFinal(preRevision.getFechaRevision());
                    TipoPrueba tipoPrueba = emMysql.find(TipoPrueba.class, pr.getTipoPrueba().getId());
                    if (pr.getTipoPrueba().getId() == 1) {
                        doView = false;
                    }
                    prueba.setTipoPrueba(tipoPrueba);
                    prueba.setHojaPruebas(hojaPruebas);
                    prueba.setUsuarioFor(usr);
                    prueba.setAbortado("N");
                    prueba.setFinalizada("N");
                    prueba.setAprobado("N");
                    lstPruebasAut.add(prueba);
                }
            }
            if (doView == true) {
                prueba = new Prueba();
                prueba.setFecha(preRevision.getFechaRevision());
                prueba.setFechaFinal(preRevision.getFechaRevision());
                TipoPrueba tipoPrueba = emMysql.find(TipoPrueba.class, 1);
                prueba.setTipoPrueba(tipoPrueba);
                prueba.setHojaPruebas(hojaPruebas);
                prueba.setUsuarioFor(usr);
                prueba.setAbortado("N");
                prueba.setFinalizada("N");
                prueba.setAprobado("N");
                lstPruebasAut.add(prueba);
            }
            prueba = new Prueba();
            prueba.setFecha(preRevision.getFechaRevision());
            prueba.setFechaFinal(preRevision.getFechaRevision());
            TipoPrueba tipoPrueba = emMysql.find(TipoPrueba.class, 3);
            prueba.setTipoPrueba(tipoPrueba);
            prueba.setHojaPruebas(hojaPruebas);
            prueba.setUsuarioFor(usr);
            prueba.setAbortado("N");
            prueba.setFinalizada("N");
            prueba.setAprobado("N");
            lstPruebasAut.add(prueba);
            Reinspeccion rein = new Reinspeccion();
            rein.setHojaPruebas(hojaPruebas);
            rein.setIntento(1);
            rein.setFechaAnterior(hojaPruebas.getFechaIngreso());
            rein.setFechaSiguiente(preRevision.getFechaRevision());
            rein.setConsecutivoRunt(" ");
            rein.setComentario(" ");
            rein.setAprobada("N");
            rein.setPreRevision(preRevision.getNoRevision());
            rein.setPruebaList(lstPruebasAut);
            emMysql.persist(rein);
            hojaPruebas.setListPruebas(lstPruebasAut);
            emMysql.merge(hojaPruebas);
        }
        return "1";
    }

    @Override
    public String servFindOrdenBienvenida(EntityManager emMysql) {
        Cda cda = emMysql.find(Cda.class, 1);
        String d = cda.getOrdenBienvenido().concat("").concat(cda.getOrdenContrato());
        return cda.getOrdenBienvenido().concat(" &% ").concat(cda.getOrdenContrato());
    }

    public Long nroTramasReportadas(Integer testSheet, EntityManager emMysql) {
        Query q = emMysql.createNamedQuery("AuditoriaSicov.ExitTrama");
        q.setParameter("idHp", testSheet);
        Long nroTramaRep = (Long) q.getSingleResult();
        return nroTramaRep;
    }

    public Long nroTramasReportadasByTest(Integer testSheet, Integer test, EntityManager emMysql) {
        Query q = emMysql.createNamedQuery("AuditoriaSicov.ExitTramaByTest");
        q.setParameter("idHp", testSheet);
        q.setParameter("test", test);
        Long nroTramaRep = (Long) q.getSingleResult();
        return nroTramaRep;
    }

    @Override
    public String BusqColoresExist(String strColores, EntityManager emMysql) {
        String result = "";
        Query q = emMysql.createNamedQuery("Color.findByDescColor");
        q.setParameter("descColor", strColores.concat("%"));
        q.setMaxResults(9);
        List<Color> lstColor = q.getResultList();
        for (Color color : lstColor) {
            result = result.concat(color.getId().toString()).concat(";").concat(color.getNombre()).concat("!");
        }
        return result;
    }

    @Override
    public String busqMarcaExist(String strMarca, EntityManager emMysql) {
        String result = "";
        Query q = emMysql.createNamedQuery("Marca.findByDescMarca");
        q.setParameter("descMarca", strMarca.concat("%"));
        q.setMaxResults(10);
        List<Marca> lstMarca = q.getResultList();
        for (Marca marca : lstMarca) {
            result = result.concat(marca.getId().toString()).concat(";").concat(marca.getNombre()).concat("!");
        }
        return result;
    }

    @Override
    public String busqAseguradoraExist(String strAseguradora, EntityManager emMysql) {
        String result = "";
        Query q = emMysql.createNamedQuery("Aseguradora.findByDescAseguradora");
        q.setParameter("descAseguradora", strAseguradora.concat("%"));
        q.setMaxResults(5);
        List<Aseguradora> lstAseguradora = q.getResultList();
        for (Aseguradora aseguradora : lstAseguradora) {
            result = result.concat(aseguradora.getId().toString()).concat(";").concat(aseguradora.getNombre()).concat("!");
        }
        return result;
    }

    @Override
    public String servFindCtxFotos(Integer ctxHojaPrueba, EntityManager emMysql) {
        Query q = emMysql.createNamedQuery("Fotos.ctxNroFoto");
        q.setParameter("ctxHP", ctxHojaPrueba);
        List<Integer> LstRevision = q.getResultList();
        Integer nroRevision;
        if (LstRevision.size() > 0) {
            nroRevision = 1 + LstRevision.iterator().next();
        } else {
            nroRevision = 1;
        }
        return nroRevision.toString();
    }

    @Override
    public String servPersisFotos(InputStream pictureFoto, Integer identPrueba, Integer identHojaPrueba, Integer numeroFoto, Integer fkUser, EntityManager emMysql) {
        HojaPruebas ctxHojaPruebas = emMysql.find(HojaPruebas.class, identHojaPrueba);
        Connection cn = getConexion();
        String sql = null;
        if (ctxHojaPruebas.getReinspeccionList().size() > 0) {
            try {
                Image imgEnbd = abrirImagen(ctxHojaPruebas.getId(), numeroFoto, cn);
                Image imgEnbdEscalada = escalarImagen(imgEnbd, 0.5D, 0.5D);

                Image imgSendClient = ImageIO.read(pictureFoto);

                Image imgNew = crearImagenEnBlanco();

                sobrePonerImagen(imgNew, imgEnbdEscalada, 0, 0);

                Image imgSendClientEscalada = escalarImagen(imgSendClient, 0.5D, 0.5D);
                sobrePonerImagen(imgNew, imgSendClientEscalada, 320, 0);

                InputStream pictureFotoNew = conversitionInputStream(imgNew);

                PreparedStatement stmt = null;
                if (numeroFoto == 3) {
                    sql = "UPDATE fotos SET Foto1=?,numeroRevision=? WHERE id_hoja_pruebas_for=?";

                } else {
                    sql = "UPDATE fotos SET Foto2=?,numeroRevision=? WHERE id_hoja_pruebas_for=?";
                }
                stmt = cn.prepareStatement(sql);
                stmt.setBinaryStream(1, pictureFotoNew);
                stmt.setInt(2, numeroFoto);
                stmt.setLong(3, identHojaPrueba);
                stmt.execute();
                if (numeroFoto == 4) {
                    Prueba prueba = emMysql.find(Prueba.class, identPrueba);
                    Calendar fecha = Calendar.getInstance();
                    prueba.setFechaFinal(fecha.getTime());
                    prueba.setFinalizada("Y");
                    UsuarioMySql usr = servFindUsuario(fkUser, emMysql);
                    prueba.setUsuarioFor(usr);
                    prueba.setAprobado("Y");
                    emMysql.merge(prueba);
                }
            } catch (Exception e) {
            }
        } else {
            try {
                PreparedStatement stmt = null;
                if (numeroFoto == 1) {
                    sql = "INSERT INTO fotos (id_hoja_pruebas_for,Foto1,numeroRevision) VALUES (?,?,?)";
                    stmt = cn.prepareStatement(sql);
                    stmt.setLong(1, identHojaPrueba);
                    stmt.setBinaryStream(2, pictureFoto);
                    stmt.setInt(3, numeroFoto);
                }
                if (numeroFoto == 2) {
                    sql = "UPDATE fotos SET Foto2=?,numeroRevision=? WHERE id_hoja_pruebas_for=?";
                    stmt = cn.prepareStatement(sql);
                    stmt.setBinaryStream(1, pictureFoto);
                    stmt.setInt(2, numeroFoto);
                    stmt.setLong(3, identHojaPrueba);
                    Prueba prueba = emMysql.find(Prueba.class, identPrueba);
                    Calendar fecha = Calendar.getInstance();
                    prueba.setFechaFinal(fecha.getTime());
                    prueba.setFinalizada("Y");
                    UsuarioMySql usr = servFindUsuario(fkUser, emMysql);
                    prueba.setUsuarioFor(usr);
                    prueba.setAprobado("Y");
                    emMysql.merge(prueba);
                    HojaPruebas ctxHp = emMysql.find(HojaPruebas.class, prueba.getHojaPruebas().getId());
                    ctxHp.setNroPruebasRegistradas(ctxHp.getNroPruebasRegistradas() + 1);
                    emMysql.merge(ctxHp);
                }
                stmt.execute();
            } catch (SQLException e) {
            }
        }
        return "1";

    }

    public Connection getConexion() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/db_cda", "root", "50lt3l3c545");
        } catch (Exception e) {
        }
        return con;
    }

    public Image abrirImagen(long idHojaPruebas, int numeroFoto, Connection conexion) {
        try {
            Image rpta = null;
            Blob imagen = null;
            String strSentencia = "";
            if (numeroFoto == 3) {
                strSentencia = "SELECT f.Foto1 FROM fotos as f WHERE f.id_hoja_pruebas_for = ?";
            } else {
                strSentencia = "SELECT f.Foto2 FROM fotos as f WHERE f.id_hoja_pruebas_for = ?";
            }
            PreparedStatement statement = conexion.prepareStatement(strSentencia);
            statement.setLong(1, idHojaPruebas);
            ResultSet rs = statement.executeQuery();
            boolean first = rs.first();
            if (first) {
                imagen = rs.getBlob(1);
                if (imagen != null) {
                    rpta = ImageIO.read(imagen.getBinaryStream());
                    return rpta;
                }
                return null;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public Image escalarImagen(Image img, double factorx, double factory) {
        AffineTransform at = AffineTransform.getScaleInstance(factorx, factory);
        int anchoImagen = img.getWidth(null);
        int altoImagen = img.getHeight(null);
        BufferedImage nuevaImagenEscalada = new BufferedImage((int) (anchoImagen * factory), (int) (altoImagen * factorx), 1);
        nuevaImagenEscalada.createGraphics().drawRenderedImage((RenderedImage) img, at);
        return nuevaImagenEscalada;
    }

    public void sobrePonerImagen(Image imgFondo, Image imgParaSobreponer, int coordx, int coordy) {
        BufferedImage bImgFondo = (BufferedImage) imgFondo;
        boolean drawImage = bImgFondo.createGraphics().drawImage(imgParaSobreponer, coordx, coordy, null);
        if (drawImage) {

        }
    }

    public InputStream conversitionInputStream(Image img) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write((RenderedImage) img, "JPEG", out);
        } catch (IOException e) {
        }
        InputStream in = new ByteArrayInputStream(out.toByteArray());
        return in;
    }

    public BufferedImage crearImagenEnBlanco() {
        BufferedImage imagenContenedora = new BufferedImage(640, 480, 1);
        Graphics2D g = imagenContenedora.createGraphics();
        g.setColor(java.awt.Color.WHITE);
        g.fillRect(0, 0, 640, 480);
        return imagenContenedora;
    }


    @Override
    public String servFindDatosRuntInMysql(String placa, EntityManager emMysql) {
        queryDao = emMysql.createNamedQuery("Vehiculo.findByCarplate");
        queryDao.setParameter("placa", placa);
        List<Vehiculo> lstVehiculo = queryDao.getResultList();
        String eve = "1";
        if (lstVehiculo.size() > 0) {
            Vehiculo ctxVehiculo = lstVehiculo.get(0);
            eve = ctxVehiculo.getPlaca().concat(";");
            eve = eve.concat(ctxVehiculo.getMarca().getNombre()).concat(";");
            eve = eve.concat(ctxVehiculo.getLineaVehiculo().getNombre()).concat(";");
            eve = eve.concat(String.valueOf(ctxVehiculo.getModelo())).concat(";");
            eve = eve.concat(String.valueOf(ctxVehiculo.getCilindraje())).concat(";");
            eve = eve.concat(ctxVehiculo.getColor().getNombre()).concat(";");
            eve = eve.concat(ctxVehiculo.getServicios().getNombre()).concat(";");
            eve = eve.concat(ctxVehiculo.getClaseVehiculo().getNombre()).concat(";");
            eve = eve.concat(ctxVehiculo.getCarroceria()).concat(";");
            eve = eve.concat(ctxVehiculo.getTipoGasolina().getNombre()).concat(";");
            eve = eve.concat(String.valueOf(ctxVehiculo.getCapacidadCarga())).concat(";");
            eve = eve.concat(ctxVehiculo.getMotor()).concat(";");
            eve = eve.concat(ctxVehiculo.getVin()).concat(";");
            eve = eve.concat(ctxVehiculo.getNroSerie()).concat(";");
            eve = eve.concat(ctxVehiculo.getChasis()).concat(";");
            eve = eve.concat(ctxVehiculo.getLicencia()).concat("; ");
            eve = eve.concat(ctxVehiculo.getPropietario().getApellidos()).concat("; ");
            eve = eve.concat(ctxVehiculo.getPropietario().getNombres()).concat("; ");
            eve = eve.concat(String.valueOf(ctxVehiculo.getPropietario().getId())).concat(";");
            eve = eve.concat(String.valueOf(ctxVehiculo.getFechaRegistro().getTime())).concat(";");
            eve = eve.concat(String.valueOf(ctxVehiculo.getEjes())).concat(";");
            eve = eve.concat(String.valueOf(ctxVehiculo.getNacionalidad())).concat(";");
            eve = eve.concat(String.valueOf(ctxVehiculo.getPropietario().getTipoIdentificacion().toString()));

        }
        return eve;
    }


    @Override
    public String servFuncTipoRevision(String strAtributos, EntityManager emMysql) {
        StringTokenizer flujo = new StringTokenizer(strAtributos, ";");
        HojaPruebas ctxHojaPruebas = emMysql.find(HojaPruebas.class, Integer.parseInt(flujo.nextToken()));
        if (ctxHojaPruebas.getPreventiva().equalsIgnoreCase("N")) {
            Long nroTrama = nroTramasReportadas(ctxHojaPruebas.getId(), emMysql);
            if (nroTrama > 0) {
                return "3";    // no puedo cambiar el edo del tipo revision,TRAMAS REPORTADAS
            }
        }
        String ctxRev;
        Query q;
        Integer nroConsec;
        HojaPruebas hojaPrueba = new HojaPruebas();
        if (ctxHojaPruebas.getPreventiva().equalsIgnoreCase("Y")) {
            ctxRev = "PREVENTIVA";
            q = emMysql.createNamedQuery("HojaPruebas.findMaxConseRTM");
            nroConsec = (Integer) q.getSingleResult();
            hojaPrueba.setCon_hoja_prueba(nroConsec + 1);
            hojaPrueba.setCon_preventiva(0);
            hojaPrueba.setPreventiva("N");
            hojaPrueba.setEstadoSICOV("INICIADO");
        } else {
            ctxRev = "RTM";
            q = emMysql.createNamedQuery("HojaPruebas.findMaxConsePrevent");
            nroConsec = (Integer) q.getSingleResult();
            hojaPrueba.setCon_hoja_prueba(0);
            hojaPrueba.setCon_preventiva(nroConsec + 1);
            hojaPrueba.setPreventiva("Y");
            hojaPrueba.setEstadoSICOV("NO_APLICA");
        }
        String usuario = flujo.nextToken().trim();
        String edoSicov = flujo.nextToken();
        hojaPrueba.setId(servFindMaxIdHP(emMysql));
        for (Prueba pr : ctxHojaPruebas.getListPruebas()) {
            Prueba ctxPr = emMysql.find(Prueba.class, pr.getId());
            ctxPr.setHojaPruebas(hojaPrueba);
        }
        hojaPrueba.setVehiculo(ctxHojaPruebas.getVehiculo());
        hojaPrueba.setPropietario(ctxHojaPruebas.getPropietario());
        hojaPrueba.setUsuario(ctxHojaPruebas.getUsuario());
        hojaPrueba.setFinalizada(ctxHojaPruebas.getFinalizada());
        hojaPrueba.setFechaIngreso(ctxHojaPruebas.getFechaIngreso());
        hojaPrueba.setAnulado(ctxHojaPruebas.getAnulado());
        hojaPrueba.setAprobado(ctxHojaPruebas.getAprobado());
        hojaPrueba.setFechaExpedicion(ctxHojaPruebas.getFechaExpedicion());
        hojaPrueba.setFechaExpiracion(ctxHojaPruebas.getFechaExpiracion());
        hojaPrueba.setFechaExpSoat(ctxHojaPruebas.getFechaExpSoat());
        hojaPrueba.setFechaVencSoat(ctxHojaPruebas.getFechaVencSoat());
        hojaPrueba.setNroIdentificacionSoat(ctxHojaPruebas.getNroIdentificacionSoat());
        hojaPrueba.setAseguradora(ctxHojaPruebas.getAseguradora());
        hojaPrueba.setConductor(ctxHojaPruebas.getConductor());
        if (ctxHojaPruebas.getComentario() != null) {
            hojaPrueba.setComentario(ctxHojaPruebas.getComentario().concat(";").concat("CAMBIO A ").concat(ctxRev).concat(" POR ").concat(usuario));
        } else {
            hojaPrueba.setComentario("CAMBIO A ".concat(ctxRev).concat(" POR ").concat(usuario));
        }
        hojaPrueba.setIntentos(ctxHojaPruebas.getIntentos());
        hojaPrueba.setUbicacionMunicipio(ctxHojaPruebas.getUbicacionMunicipio());
        if (ctxHojaPruebas.getResponsable() != null) {
            hojaPrueba.setResponsable(ctxHojaPruebas.getResponsable());
        }
        if (ctxHojaPruebas.getPin() != null) {
            hojaPrueba.setPin(ctxHojaPruebas.getPin());
        }
        if (ctxHojaPruebas.getEstado() != null) {
            hojaPrueba.setEstado(ctxHojaPruebas.getEstado());
        }
        hojaPrueba.setFormaMedTemperatura(ctxHojaPruebas.getFormaMedTemperatura());
        emMysql.persist(hojaPrueba);
        ctxHojaPruebas.setAnulado("Y");
        ctxHojaPruebas.setEstado("Anulado");
        ctxHojaPruebas.setEstadoSICOV("Fallido");
        emMysql.merge(ctxHojaPruebas);
        return hojaPrueba.getId().toString().concat(";").concat(hojaPrueba.getEstadoSICOV());
    }

    @Override
    public String servFuncCambioPlaca(String strAtributos, EntityManager emMysql) {
        StringTokenizer flujo = new StringTokenizer(strAtributos, ";");
        HojaPruebas hojaPruebas = emMysql.find(HojaPruebas.class, Integer.parseInt(flujo.nextToken()));
        String usuario = flujo.nextToken();
        String placa = flujo.nextToken();
        queryDao = emMysql.createNamedQuery("Vehiculo.findByCarplate");
        queryDao.setParameter("placa", placa.trim());
        List<Vehiculo> ctxVehiculo = queryDao.getResultList();
        if (ctxVehiculo.size() > 0) {
            hojaPruebas.setVehiculo(ctxVehiculo.get(0));
        } else {
            Vehiculo ctxSameVehiculo = hojaPruebas.getVehiculo();
            ctxSameVehiculo.setPlaca(placa);
            hojaPruebas.setVehiculo(ctxSameVehiculo);
        }

        if (hojaPruebas.getComentario() == null) {
            hojaPruebas.setComentario("CAMBIO DE PLACA POR ".concat(usuario.trim()).concat(" antes estuvo asociada con la placa ").concat(hojaPruebas.getVehiculo().getPlaca()));
        } else {
            hojaPruebas.setComentario("CAMBIO DE PLACA POR ".concat(usuario.trim()).concat(" antes estuvo asociada con la palca  ").concat(hojaPruebas.getVehiculo().getPlaca()).concat(";").concat(hojaPruebas.getComentario()));
        }
        emMysql.merge(hojaPruebas);
        return "1";

    }


    @Override
    public String serPersitProfLabradoLiviano(String strProfundidaLabrado, EntityManager emMysql) {

        String eve = "event 1";
        StringTokenizer flujoPrueba = new StringTokenizer(strProfundidaLabrado, "/");

        Integer ctxPrueba = Integer.parseInt(flujoPrueba.nextToken());

        Prueba prueba = emMysql.find(Prueba.class, ctxPrueba);

        StringTokenizer proLLantas = new StringTokenizer(flujoPrueba.nextToken(), "!");
        prueba.setComentario(proLLantas.nextToken());
        prueba.setPuntoLabradoRepuesto(proLLantas.nextToken());
        StringTokenizer proMinLLantas = new StringTokenizer(proLLantas.nextToken(), "$");
        Float DD = Float.parseFloat(proMinLLantas.nextToken());
        Float DI = Float.parseFloat(proMinLLantas.nextToken());
        Float TD = Float.parseFloat(proMinLLantas.nextToken());
        Float TI = Float.parseFloat(proMinLLantas.nextToken());
        String ctxResp = proMinLLantas.nextToken();


        Float llRr = 0.0F;
        if (!ctxResp.equalsIgnoreCase("NA")) {
            llRr = Float.parseFloat(ctxResp);
        }

        String strLabrado = "D.Izq".concat(String.valueOf(DI)).concat("$").concat("D.Der").concat(String.valueOf(DD)).concat("&").concat("T.Izq").concat(String.valueOf(TI)).concat("$").concat("T.Der").concat(String.valueOf(TD)).concat("$").concat("RESP. ").concat(ctxResp);
        if (prueba.getObservaciones() != null) {
            String[] obs = prueba.getObservaciones().split("obs");
            if (obs.length > 1) {
                prueba.setObservaciones(strLabrado.concat("obs").concat(obs[1]));
            } else {
                prueba.setObservaciones(strLabrado);
            }
        } else {
            prueba.setObservaciones(strLabrado);
        }

        Double refLabrado;
        Integer cdgDefecto;
        if (prueba.getHojaPruebas().getVehiculo().getPesoBruto() > 3500) {
            refLabrado = 2.0;
            cdgDefecto = 10095;
        } else {
            refLabrado = 1.6;
            cdgDefecto = 10094;
        }
        emMysql.merge(prueba);
        if (DD < refLabrado) {
            eve = "event en DD < 1";
            DefxpruebaPK defxpruebaPK = new DefxpruebaPK(cdgDefecto, ctxPrueba);
            Defxprueba Defxprueba = emMysql.find(Defxprueba.class, defxpruebaPK);
            if (Defxprueba == null) {
                Defxprueba defxpruebaNew = new Defxprueba(cdgDefecto, ctxPrueba);
                emMysql.persist(defxpruebaNew);
            }
            eve = "event en DD < 1 final";
            return "ctxLLDI";
        } else {
            eve = "event DD";
            DefxpruebaPK defxpruebaPK = new DefxpruebaPK(cdgDefecto, ctxPrueba);
            Defxprueba Defxprueba = emMysql.find(Defxprueba.class, defxpruebaPK);
            if (Defxprueba != null) {
                emMysql.remove(Defxprueba);
            }

        }
        if (!ctxResp.equalsIgnoreCase("N/A")) {
            if (llRr < refLabrado) {
                eve = "event en DD < 1";
                DefxpruebaPK defxpruebaPK = new DefxpruebaPK(cdgDefecto, ctxPrueba);
                Defxprueba Defxprueba = emMysql.find(Defxprueba.class, defxpruebaPK);
                if (Defxprueba == null) {
                    Defxprueba defxpruebaNew = new Defxprueba(cdgDefecto, ctxPrueba);
                    emMysql.persist(defxpruebaNew);
                }
                eve = "event en DD < 1 final";
                return "ctxLLDI";
            } else {
                eve = "event DD";
                DefxpruebaPK defxpruebaPK = new DefxpruebaPK(cdgDefecto, ctxPrueba);
                Defxprueba Defxprueba = emMysql.find(Defxprueba.class, defxpruebaPK);
                if (Defxprueba != null) {
                    emMysql.remove(Defxprueba);
                }

            }
        }

        if (DI < refLabrado) {
            eve = "event en DD < 1";
            DefxpruebaPK defxpruebaPK = new DefxpruebaPK(cdgDefecto, ctxPrueba);
            Defxprueba Defxprueba = emMysql.find(Defxprueba.class, defxpruebaPK);
            if (Defxprueba == null) {
                Defxprueba defxpruebaNew = new Defxprueba(cdgDefecto, ctxPrueba);
                emMysql.persist(defxpruebaNew);
            }
            eve = "event en DD < 1 final";
            return "ctxLLDI";
        } else {
            eve = "event DD";
            DefxpruebaPK defxpruebaPK = new DefxpruebaPK(cdgDefecto, ctxPrueba);
            Defxprueba Defxprueba = emMysql.find(Defxprueba.class, defxpruebaPK);
            if (Defxprueba != null) {
                emMysql.remove(Defxprueba);
            }

        }


        if (TD < refLabrado) {
            eve = "event en TD < 1.6";
            DefxpruebaPK defxpruebaPK = new DefxpruebaPK(cdgDefecto, ctxPrueba);
            Defxprueba Defxprueba = emMysql.find(Defxprueba.class, defxpruebaPK);
            if (Defxprueba == null) {
                Defxprueba defxpruebaNew = new Defxprueba(cdgDefecto, ctxPrueba);
                emMysql.persist(defxpruebaNew);
            }
            eve = "event en TD < 1.6 final";
            return "ctxLLDI";
        } else {
            eve = "event DD";
            DefxpruebaPK defxpruebaPK = new DefxpruebaPK(cdgDefecto, ctxPrueba);
            Defxprueba Defxprueba = emMysql.find(Defxprueba.class, defxpruebaPK);
            if (Defxprueba != null) {
                emMysql.remove(Defxprueba);
            }
            eve = "event DD fianl";
        }


        if (TI < refLabrado) {
            eve = "event en TD < " + refLabrado;
            DefxpruebaPK defxpruebaPK = new DefxpruebaPK(cdgDefecto, ctxPrueba);
            Defxprueba Defxprueba = emMysql.find(Defxprueba.class, defxpruebaPK);
            if (Defxprueba == null) {
                Defxprueba defxpruebaNew = new Defxprueba(cdgDefecto, ctxPrueba);
                emMysql.persist(defxpruebaNew);
            }
            eve = "event en TD < 1.6 final";
            return "1";
        } else {
            eve = "event DD";
            DefxpruebaPK defxpruebaPK = new DefxpruebaPK(cdgDefecto, ctxPrueba);
            Defxprueba Defxprueba = emMysql.find(Defxprueba.class, defxpruebaPK);
            if (Defxprueba != null) {
                emMysql.remove(Defxprueba);
            }
            eve = "event DD fianl";
        }
        return "ctxLLDI";


    }//fin del metodo

    @Override
    public String serPersitProfLabrado(String strProfundidaLabrado, EntityManager emMysql) {
        StringTokenizer flujo = new StringTokenizer(strProfundidaLabrado, "!");
        String eve = "event 1";

        StringTokenizer flujoPrueba = new StringTokenizer(flujo.nextToken(), "/");

        Integer ctxPrueba = Integer.parseInt(flujoPrueba.nextToken());

        Prueba prueba = emMysql.find(Prueba.class, ctxPrueba);
        String strProfundidad = flujo.nextToken();
        if (prueba.getObservaciones() != null) {
            String[] obs = prueba.getObservaciones().split("obs");
            if (obs.length > 1) {
                prueba.setObservaciones(strProfundidad.concat("obs").concat(obs[1]));
            } else {
                prueba.setObservaciones(strProfundidad);
            }
        } else {
            prueba.setObservaciones(strProfundidad);
        }

        prueba.setComentario(flujoPrueba.nextToken());
        emMysql.merge(prueba);
        StringTokenizer proLLantas1 = new StringTokenizer(strProfundidad, "&");

        Double DD = Double.parseDouble(proLLantas1.nextToken());
        if (DD < 1.0) {
            eve = "event en DD < 1";
            DefxpruebaPK defxpruebaPK = new DefxpruebaPK(14016, ctxPrueba);
            Defxprueba Defxprueba = emMysql.find(Defxprueba.class, defxpruebaPK);
            if (Defxprueba == null) {
                Defxprueba defxpruebaNew = new Defxprueba(14016, ctxPrueba);
                emMysql.persist(defxpruebaNew);
            }
            eve = "event en DD < 1 final";
            return "1";
        } else {
            eve = "event DD";
            DefxpruebaPK defxpruebaPK = new DefxpruebaPK(14016, ctxPrueba);
            Defxprueba Defxprueba = emMysql.find(Defxprueba.class, defxpruebaPK);
            if (Defxprueba != null) {
                emMysql.remove(Defxprueba);
            }
            eve = "event DD fianl";
        }
        StringTokenizer proLLantas2 = new StringTokenizer(proLLantas1.nextToken(), "$");
        Double TD = Double.parseDouble(proLLantas2.nextToken());
        eve = "event ".concat(String.valueOf(TD));
        if (TD < 1.0) {
            DefxpruebaPK defxpruebaPK = new DefxpruebaPK(14016, ctxPrueba);
            Defxprueba Defxprueba = emMysql.find(Defxprueba.class, defxpruebaPK);
            if (Defxprueba == null) {
                Defxprueba defxpruebaNew = new Defxprueba(14016, ctxPrueba);
                emMysql.persist(defxpruebaNew);
            }
            return "1";
        }
        return "1";
    }

    @Override
    public String servCtxShowDefectos(Integer ctxPruebaSensorial, Integer ctxTipoVehiculo, Integer ctxPartVehiculo, Integer ctxTipoMiga, EntityManager emMysql) {
        respImplDao = "";

        queryDao = emMysql.createNamedQuery("Defxprueba.findIdPrueba");
        queryDao.setParameter("ctxPrueba", ctxPruebaSensorial);

        List<Defxprueba> lstDefxprueba = queryDao.getResultList();

        queryDao = emMysql.createNamedQuery("Defectos.findByCtxSensorial");
        queryDao.setParameter("tipoVehiculo", ctxTipoVehiculo);
        queryDao.setParameter("particionVehiculo", ctxPartVehiculo);
        queryDao.setParameter("tipoMiga", ctxTipoMiga);
        String exitDefc = "";
        List<Defectos> ctxDefectos = queryDao.getResultList();
        for (Defectos d : ctxDefectos) {
            exitDefc = "0";
            for (Defxprueba dxP : lstDefxprueba) {
                if (d.getCodigoSuperintendencia().equalsIgnoreCase(dxP.getDefectos().getCodigoSuperintendencia())) {
                    exitDefc = "1";
                    break;
                }
            }
            respImplDao = respImplDao.concat(String.valueOf(d.getCardefault())).concat(";").concat(d.getNombreproblema()).concat(";").concat(d.getTipodefecto()).concat(";").concat(d.getCodigoSuperintendencia()).concat(";").concat(exitDefc).concat("$");
        }
        return respImplDao;
    }

    @Override
    public String servSetDefectoPrueba(String ctxFuncion, Integer ctxIdPrueba, Integer ctxDefecto, EntityManager emMysql) {
        respImplDao = "0";
        queryDao = emMysql.createNamedQuery("Defxprueba.findIdPrueba");
        queryDao.setParameter("ctxPrueba", ctxIdPrueba);
        List<Defxprueba> lstDefxprueba = queryDao.getResultList();
        boolean exitDefecto = false;
        Defectos defxPruebaCtx = emMysql.find(Defectos.class, ctxDefecto);
        for (Defxprueba dxP : lstDefxprueba) {
            if (dxP.getDefectos().getCodigoSuperintendencia().equalsIgnoreCase(defxPruebaCtx.getCodigoSuperintendencia())) {
                exitDefecto = true;
            }
        }

        if (lstDefxprueba.size() == 0 & ctxFuncion.equalsIgnoreCase("add")) {
            Defxprueba defxpruebaNew = new Defxprueba(ctxDefecto, ctxIdPrueba);
            emMysql.persist(defxpruebaNew);
            respImplDao = "1";
        } else {
            if (exitDefecto == false & ctxFuncion.equalsIgnoreCase("add")) {
                Defxprueba defxpruebaNew = new Defxprueba(ctxDefecto, ctxIdPrueba);
                emMysql.persist(defxpruebaNew);
                respImplDao = "1";
            }

            if (exitDefecto == true & ctxFuncion.equalsIgnoreCase("remove")) {
                DefxpruebaPK defxpruebaPK = new DefxpruebaPK(ctxDefecto, ctxIdPrueba);
                Defxprueba Defxprueba = emMysql.find(Defxprueba.class, defxpruebaPK);
                emMysql.remove(Defxprueba);
                respImplDao = "2";
            }
        }
        return respImplDao;
    }


    @Override
    public String servSetCierrePruebaSesonrial(Integer ctxIdPrueba, Integer ctxUsuario, EntityManager emMysql, EntityManager em) {
        respImplDao = "0";
        String eve = "1";
        Integer nvoAudi = 0;
        Integer idAud = 0;

        String decisionSensorial = "Y";
        String ipPruebas;
        UbicacionRecursos ubic;
        Prueba prueba = emMysql.find(Prueba.class, ctxIdPrueba);
        if (prueba.getDefxpruebaList().size() > 0) {
            decisionSensorial = "N";
        }
        if (prueba.getHojaPruebas().getVehiculo().getCategoria() == 4) {
            ubic = em.find(UbicacionRecursos.class, 1);
        } else {
            ubic = em.find(UbicacionRecursos.class, 2);
        }
        Calendar fecha = Calendar.getInstance();
        prueba.setFechaFinal(fecha.getTime());
        prueba.setFinalizada("Y");
        UsuarioMySql usr = servFindUsuario(ctxUsuario, emMysql);
        prueba.setUsuarioFor(usr);
        prueba.setAprobado(decisionSensorial);
        SEQUENCE s = emMysql.find(SEQUENCE.class, "AUD_SICOV");
        idAud = s.getSEQCOUNT();
        nvoAudi = idAud + 1;
        s.setSEQCOUNT(nvoAudi);
        eve = "9";
        emMysql.merge(s);
        eve = "10";
        prueba.setFechaAborto(ubic.getIpVisual().concat(";").concat(String.valueOf(idAud)));
        eve = "11";
        emMysql.merge(prueba);
        respImplDao = "1";
        return respImplDao;
    }

    @Override
    public String servSetObservacionesPruebas(Integer ctxIdPrueba, String observaciones, EntityManager emMysql) {
        respImplDao = "0";
        Prueba prueba = emMysql.find(Prueba.class, ctxIdPrueba);
        if (prueba.getObservaciones() != null) {
            String[] obs = prueba.getObservaciones().split("obs");
            prueba.setObservaciones(obs[0].concat("").concat(observaciones));
        } else {
            prueba.setObservaciones(observaciones);
        }
        emMysql.merge(prueba);
        return respImplDao;
    }


    @Override
    public String servFindEstadoPruebas(Integer ctxIdPrueba, EntityManager emMysql) {
        respImplDao = "0";
        Prueba prueba = emMysql.find(Prueba.class, ctxIdPrueba);
        respImplDao = "1";
        String observ = "";
        String labrado = "x";
        String labradoRep = "x";
        if (prueba.getObservaciones() != null) {
            observ = prueba.getObservaciones();
        }
        if (prueba.getComentario() != null) {
            labrado = prueba.getComentario();
        }
        if (prueba.getPuntoLabradoRepuesto() != null) {
            labradoRep = prueba.getPuntoLabradoRepuesto();
        }
        respImplDao = prueba.getAprobado().concat(";").concat(observ).concat(";").concat(labrado).concat(";").concat(labradoRep);
        return respImplDao;
    }

    @Override
    public String servFuncAnularRTM(String strAtributos, EntityManager emMysql) {
        StringTokenizer flujo = new StringTokenizer(strAtributos, ";");
        HojaPruebas hojaPruebas = emMysql.find(HojaPruebas.class, Integer.parseInt(flujo.nextToken()));
        String usuario = flujo.nextToken();
        String cometarioAnulacion = flujo.nextToken();
        if (hojaPruebas.getReinspeccionList().size() == 0) {
            hojaPruebas.setFinalizada("Y");
            hojaPruebas.setEstadoSICOV("NO_APLICA");
            hojaPruebas.setEstado("ANULADO");
            hojaPruebas.setAnulado("Y");
            if (hojaPruebas.getComentario() == null) {
                hojaPruebas.setComentario("Hoja Prueba Anulada POR ".concat(usuario.trim()).concat(" debido a: ").concat(cometarioAnulacion));
            } else {
                hojaPruebas.setComentario("Hoja Prueba Anulada POR ".concat(usuario.trim()).concat(" debido a: ").concat(cometarioAnulacion).concat(";").concat(hojaPruebas.getComentario()));
            }
            List<Prueba> lstPruebas = hojaPruebas.getListPruebas();
            for (Prueba prueba : lstPruebas) {
                prueba.setAbortado("A");
                prueba.setFinalizada("A");
                prueba.setAutorizada("N");
                prueba.setObservaciones(cometarioAnulacion);
                Query q = emMysql.createQuery("SELECT a FROM AuditoriaSicov a WHERE a.idRevision= :idHp AND a.tipoEvento= :idEvento ");
                q.setParameter("idHp", prueba.getHojaPruebas().getId());
                q.setParameter("idEvento", prueba.getTipoPrueba().getCodEventoSicov());
                List<AuditoriaSicov> lstEv = q.getResultList();
                int posTrama = 0;
                String idPrueba;
                for (AuditoriaSicov auScv : lstEv) {
                    posTrama = auScv.getTRAMA().indexOf("idRegistro");
                    idPrueba = auScv.getTRAMA().substring(posTrama + 13, auScv.getTRAMA().length() - 2);
                    if (prueba.getId() == Integer.parseInt(idPrueba)) {
                        auScv.setObservacion("Hoja de Prueba Anulada ".concat(cometarioAnulacion));
                        emMysql.merge(auScv);

                    }
                }
            }
        } else {
            Reinspeccion reinpeccion = hojaPruebas.getReinspeccionList().iterator().next();
            List<Prueba> lstPruebas = reinpeccion.getPruebaList();
            hojaPruebas.setEstadoSICOV("SINCRONIZADO");
            hojaPruebas.setFinalizada("Y");
            hojaPruebas.setIntentos(1);
            if (hojaPruebas.getComentario() == null) {
                hojaPruebas.setComentario("Reinspeccion  Anulada POR ".concat(usuario.trim()).concat(" debido a: ").concat(cometarioAnulacion));
            } else {
                hojaPruebas.setComentario("Reinspeccion  Anulada POR ".concat(usuario.trim()).concat(" debido a: ").concat(cometarioAnulacion).concat(";").concat(hojaPruebas.getComentario()));
            }
            for (Prueba prueba : lstPruebas) {
                Query q = emMysql.createQuery("SELECT a FROM AuditoriaSicov a WHERE a.idRevision= :idHp AND a.tipoEvento= :idEvento ");
                q.setParameter("idHp", prueba.getHojaPruebas().getId());
                q.setParameter("idEvento", prueba.getTipoPrueba().getCodEventoSicov());
                List<AuditoriaSicov> lstEv = q.getResultList();
                int posTrama = 0;
                String idPrueba;
                for (AuditoriaSicov auScv : lstEv) {
                    posTrama = auScv.getTRAMA().indexOf("idRegistro");
                    idPrueba = auScv.getTRAMA().substring(posTrama + 13, auScv.getTRAMA().length() - 2);
                    if (prueba.getId() == Integer.parseInt(idPrueba)) {
                        auScv.setObservacion("Hoja de Prueba Anulada ".concat(cometarioAnulacion));
                        emMysql.merge(auScv);
                    }
                }
            }
            reinpeccion = emMysql.find(Reinspeccion.class, reinpeccion.getId());
            emMysql.remove(reinpeccion);
        }
        emMysql.merge(hojaPruebas);
        return "1";
    }

    @Override
    public String servFuncCorregirPin(String strAtributos, EntityManager emMysql) {
        StringTokenizer flujo = new StringTokenizer(strAtributos, ";");
        HojaPruebas hojaPruebas = emMysql.find(HojaPruebas.class, Integer.parseInt(flujo.nextToken()));
        String usuario = flujo.nextToken();
        String nroPin = flujo.nextToken();
        if (hojaPruebas.getComentario() == null) {
            hojaPruebas.setComentario("CAMBIO NRO PIN POR ".concat(usuario.trim()).concat(" a ").concat(nroPin));
        } else {
            hojaPruebas.setComentario("CAMBIO NRO PIN POR ".concat(usuario.trim()).concat(" a ").concat(nroPin).concat(";").concat(hojaPruebas.getComentario()));
        }
        hojaPruebas.setPin(nroPin);
        emMysql.merge(hojaPruebas);
        return "1";
    }

    @Override
    public String servFuncCmbEdoSicov(String strAtributos, EntityManager emMysql) {
        StringTokenizer flujo = new StringTokenizer(strAtributos, ";");
        HojaPruebas hojaPruebas = emMysql.find(HojaPruebas.class, Integer.parseInt(flujo.nextToken()));
        String usuario = flujo.nextToken();
        String edoSicov = flujo.nextToken();
        if (edoSicov.equalsIgnoreCase("Env1FUR")) {
            Long nroTrama = nroTramasReportadasByTest(hojaPruebas.getId(), 8, emMysql);
            if (nroTrama == 0) {
                return "3";    // no puedo cambiar el edo del tipo revision,NO HAY TRAMAS REPORTADAS
            }
        }
        if (edoSicov.equalsIgnoreCase("SINCRONIZADO")) {
            Long nroTrama = nroTramasReportadasByTest(hojaPruebas.getId(), 1, emMysql);
            if (nroTrama == 0) {
                return "3";    // no puedo cambiar el edo del tipo revision,NO HAY TRAMAS REPORTADAS
            }
        }

        if (hojaPruebas.getComentario() == null) {
            hojaPruebas.setComentario("CAMBIO ESTADO SICOV POR ".concat(usuario.trim()).concat(" a ").concat(edoSicov));
        } else {
            hojaPruebas.setComentario("CAMBIO ESTADO SICOV POR ".concat(usuario.trim()).concat(" a ").concat(edoSicov).concat(";").concat(hojaPruebas.getComentario()));
        }
        hojaPruebas.setEstadoSICOV(edoSicov);
        if (edoSicov.equalsIgnoreCase("INICIADO")) {
            hojaPruebas.setFinalizada("N");
        }
        if (edoSicov.equalsIgnoreCase("SINCRONIZADO")) {
            hojaPruebas.setFinalizada("Y");
        }
        emMysql.merge(hojaPruebas);
        return "1";
    }

    @Override
    public String servEstadisticaHojaPruebaFor1Vez(String tipoRevision, Date fechaInicial, Date fechaFinal, EntityManager emMysql) {
        Integer cntHpAprob = 0;
        Integer cntHpReprob = 0;
        Integer cntRein = 0;
        String respEstadistica = "";
        queryDao = emMysql.createNamedQuery("HojaPruebas.EstadisticaHojaPruebaActiva");
        queryDao.setParameter("tipoRevision", tipoRevision);
        queryDao.setParameter("fechaInicial", fechaInicial);
        queryDao.setParameter("fechaFinal", fechaFinal);
        List<HojaPruebas> lstHoja = queryDao.getResultList();
        if (lstHoja.size() > 0) {
            for (HojaPruebas hp : lstHoja) {
                if (hp.getAprobado().equalsIgnoreCase("Y") & hp.getReinspeccionList().size() == 0) {
                    cntHpAprob = cntHpAprob + 1;
                }
                if (hp.getAprobado().equalsIgnoreCase("N") & hp.getReinspeccionList().size() == 0) {
                    cntHpReprob = cntHpReprob + 1;
                }
                if (hp.getReinspeccionList().size() > 0) {
                    cntHpReprob = cntHpReprob + 1;
                }
            }
            respEstadistica = String.valueOf(cntHpAprob).concat(";").concat(String.valueOf(cntHpReprob));
			/*queryDao  = emMysql.createNamedQuery("Reinspeccion.findByFechaSiguiente");
			queryDao.setParameter("fechainicial",fechaInicial);
			List<Reinspeccion> lstReinspeccion = queryDao.getResultList();
			if(lstReinspeccion.size()>0){
				for (Reinspeccion r : lstReinspeccion) {
					cntRein =cntRein +1;
				}
			}
			respEstadistica= respEstadistica.concat(";").concat(String.valueOf(cntRein)) ;*/
        } else {
            respEstadistica = "0";
        }
        return respEstadistica;
    }

    @Override
    public String servViewDetallesMedidasbyTipoPruebas(Integer tipoPrueba, Date fechaInicial, Date fechaFinal, EntityManager emMysql) {
        String respEstadistica = "";
        return respEstadistica;

    }

    @Override
    public String servEstadisticaAnalisisPruebas(Integer tipoPrueba, Date fechaInicial, Date fechaFinal, EntityManager emMysql) {
        Integer cntPrAprob = 0;
        Integer cntPrReprob = 0;
        Integer cntPrReinAprob = 0;
        Integer cntPrReinReprob = 0;
        String respEstadistica = "";
        Boolean tuvoRein = false;
        queryDao = emMysql.createNamedQuery("Pruebas.findByFechasAndTypo");
        queryDao.setParameter("tipoPrueba", tipoPrueba);
        Calendar fechaIni = Calendar.getInstance();
        fechaIni.setTime(fechaInicial);
        fechaIni.set(Calendar.HOUR, 5);
        fechaIni.set(Calendar.HOUR_OF_DAY, 5);
        fechaIni.set(Calendar.MINUTE, 10);
        Calendar fechaFin = Calendar.getInstance();
        fechaFin.setTime(fechaFinal);
        fechaFin.set(Calendar.HOUR, 23);
        fechaFin.set(Calendar.HOUR_OF_DAY, 23);
        fechaFin.set(Calendar.MINUTE, 30);
        queryDao.setParameter("fechaInicial", fechaIni.getTime());
        queryDao.setParameter("fechaFinal", fechaFin.getTime());
        List<Prueba> lstPrueba = queryDao.getResultList();
        if (lstPrueba.size() > 0) {
            for (Prueba pr : lstPrueba) {
                if (pr.getAprobado().equalsIgnoreCase("Y") & pr.getHojaPruebas().getReinspeccionList().size() == 0) {
                    cntPrAprob = cntPrAprob + 1;
                }
                if (pr.getAprobado().equalsIgnoreCase("N") & pr.getHojaPruebas().getReinspeccionList().size() == 0) {
                    cntPrReprob = cntPrReprob + 1;
                }
                if (pr.getHojaPruebas().getReinspeccionList().size() > 0) {
                    Reinspeccion reins = (Reinspeccion) pr.getHojaPruebas().getReinspeccionList().iterator().next();
                    tuvoRein = false;
                    for (Prueba prR : reins.getPruebaList()) {
                        if (prR.getTipoPrueba().getId() == tipoPrueba) {
                            if (prR.getAprobado().equalsIgnoreCase("Y")) {
                                cntPrReinAprob = cntPrReinAprob + 1;
                                cntPrReprob = cntPrReprob + 1;
                                tuvoRein = true;
                            }
                            if (prR.getAprobado().equalsIgnoreCase("N")) {
                                cntPrReinReprob = cntPrReinReprob + 1;
                                cntPrReprob = cntPrReprob + 1;
                                tuvoRein = true;
                            }
                        }//valida si es el tipodePrueba a estudiar
                    }
                    if (tuvoRein == false) {
                        cntPrAprob = cntPrAprob + 1;
                    }
                }//si esta en contexto de reinspeccion
            }
            respEstadistica = cntPrAprob.toString().concat(";").concat(cntPrReprob.toString()).concat(";").concat(cntPrReinAprob.toString()).concat(";").concat(cntPrReinReprob.toString());
        } else {
            respEstadistica = "0";
        }
        return respEstadistica;
    }

    @Override
    public String servEstadisticaMovRTM(String tipoRevision, Date fechaInicial, Date fechaFinal, EntityManager emMysql) {
        Integer cntHpAprob = 0;
        Integer cntHpReprob = 0;
        Integer cntRein = 0;
        String respEstadistica = "";
        queryDao = emMysql.createNamedQuery("HojaPruebas.EstadisticaHojaPruebaActiva");
        queryDao.setParameter("tipoRevision", tipoRevision);
        queryDao.setParameter("fechaInicial", fechaInicial);
        queryDao.setParameter("fechaFinal", fechaFinal);
        List<HojaPruebas> lstHoja = queryDao.getResultList();
        if (lstHoja.size() > 0) {
            for (HojaPruebas hp : lstHoja) {
                if (hp.getAprobado().equalsIgnoreCase("Y") & hp.getReinspeccionList().size() == 0) {
                    cntHpAprob = cntHpAprob + 1;
                }
                if (hp.getAprobado().equalsIgnoreCase("N") & hp.getReinspeccionList().size() == 0) {
                    cntHpReprob = cntHpReprob + 1;
                }
                if (hp.getReinspeccionList().size() > 0) {
                    cntHpReprob = cntHpReprob + 1;
                }
            }
            respEstadistica = String.valueOf(cntHpAprob).concat(";").concat(String.valueOf(cntHpReprob));
            queryDao = emMysql.createNamedQuery("Reinspeccion.findByFechaSiguiente");
            queryDao.setParameter("fechainicial", fechaInicial);
            queryDao.setParameter("fechafinal", fechaFinal);
            List<Reinspeccion> lstReinspeccion = queryDao.getResultList();
            if (lstReinspeccion.size() > 0) {
                for (Reinspeccion r : lstReinspeccion) {
                    cntRein = cntRein + 1;
                }
            }
            respEstadistica = respEstadistica.concat(";").concat(String.valueOf(cntRein));
        } else {
            respEstadistica = "0";
        }
        return respEstadistica;
    }


    @Override
    public String servInformeRevision(Date fechaInicial, Date fechaFinal, EntityManager emMysql) {
        Integer cntHpRTM = 0;
        Integer cntHpPrevt = 0;
        String respEstadistica = "";
        queryDao = emMysql.createNamedQuery("HojaPruebas.InformeRevision");
        queryDao.setParameter("fechaInicial", fechaInicial);
        queryDao.setParameter("fechaFinal", fechaFinal);
        List<HojaPruebas> lstHoja = queryDao.getResultList();
        if (lstHoja.size() > 0) {
            for (HojaPruebas hp : lstHoja) {
                if (hp.getPreventiva().equalsIgnoreCase("N")) {
                    cntHpRTM = cntHpRTM + 1;
                }
                if (hp.getPreventiva().equalsIgnoreCase("Y")) {
                    cntHpPrevt = cntHpPrevt + 1;
                }
            }
            respEstadistica = String.valueOf(cntHpRTM).concat(";").concat(String.valueOf(cntHpPrevt));
        } else {
            respEstadistica = "0";
        }
        return respEstadistica;
    }

    @Override
    public String servFindFuncConsola(String placa, EntityManager emMysql) {
        Calendar calenInic = Calendar.getInstance();
        Calendar calenFin = Calendar.getInstance();
        LocalDate date = calenInic.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        TemporalUnit unidadTemporal = ChronoUnit.DAYS;
        LocalDate dateResultado = date.minus(15, unidadTemporal);
        calenInic.setTime(Date.from(dateResultado.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        queryDao = emMysql.createNamedQuery("HojaPruebas.funcConsolaEntrada");
        queryDao.setParameter("placa", placa);
        queryDao.setParameter("fechaInicial", calenInic.getTime());
        queryDao.setParameter("fechaFinal", calenFin.getTime());
        List<HojaPruebas> lstHoja = queryDao.getResultList();
        if (lstHoja.size() > 0) {
            HojaPruebas hojaPruebas = (HojaPruebas) queryDao.getResultList().iterator().next();
            String strCda = this.servFindCtxCDA(emMysql);
            String[] arrCda = strCda.split(";");
            return hojaPruebas.getPreventiva().concat(";").concat(hojaPruebas.getVehiculo().getClaseVehiculo().getNombre().concat(";")).concat(hojaPruebas.getVehiculo().getPropietario().getApellidos().concat(", ").concat(hojaPruebas.getVehiculo().getPropietario().getNombres()).concat(";").concat(hojaPruebas.getId().toString()).concat(";")).concat(hojaPruebas.getEstadoSICOV()).concat(";").concat(String.valueOf(hojaPruebas.getFechaIngreso().getTime())).concat(";").concat(hojaPruebas.getVehiculo().getServicios().getNombre()).concat(";").concat(arrCda[4].trim());
        } else {
            queryDao = emMysql.createNamedQuery("HojaPruebas.findByPlaca");
            queryDao.setParameter("placa", placa);
            lstHoja = queryDao.getResultList();
            if (lstHoja.size() > 0) {
                return "1";
            } else {
                return "0";
            }
        }
    }

    @Override
    public String servFindPropietarioExistente(String cedula, EntityManager emMysql) {
        String respPropietario = null;
        if (cedula.length() > 0) {
            Propietario propietario = emMysql.find(Propietario.class, Long.parseLong(cedula));
            if (propietario != null) {
                respPropietario = propietario.getId().toString().concat(";").concat(propietario.getApellidos()).concat(";").concat(propietario.getNombres()).concat(";").concat(propietario.getDireccion()).concat(";").concat(propietario.getEmail()).concat(";").concat(propietario.getTelefono());
            } else {
                respPropietario = "0";
            }
        } else {
            respPropietario = "0";
        }
        return respPropietario;
    }

    private LineaVehiculo serFindLineasVehiculo(String strLinea, EntityManager emMysql, Marca marca) {
        queryDao = emMysql.createNamedQuery("LineaVehiculo.findByCrlname");
        queryDao.setParameter("crlname", strLinea);

        List<LineaVehiculo> lstLineaVehiculo = queryDao.getResultList();
        if (lstLineaVehiculo.size() > 0) {
            LineaVehiculo lineaVehiculo = (LineaVehiculo) queryDao.getResultList().iterator().next();
            return lineaVehiculo;
        } else {
            queryDao = emMysql.createNamedQuery("LineaVehiculo.findMaxId");
            Integer maxLinea = (Integer) queryDao.getSingleResult();
            maxLinea++;
            Query query = emMysql.createNativeQuery("INSERT INTO lineas_vehiculos (CARLINE,CRLCOD,CRLNAME,CARMARK) " + " VALUES(?,?,?,?)");
            query.setParameter(1, maxLinea);
            query.setParameter(2, maxLinea.toString());
            query.setParameter(3, strLinea);
            query.setParameter(4, marca.getId());
            query.executeUpdate();
            emMysql.flush();
            LineaVehiculo lineaVehiculo = emMysql.find(LineaVehiculo.class, maxLinea);
            return lineaVehiculo;
        }
    }

    private Marca serFindMarca(String marca, EntityManager emMysql) {
        queryDao = emMysql.createNamedQuery("Marca.findByNombremarca");
        queryDao.setParameter("nombre", marca);
        List<Marca> lstMarca = queryDao.getResultList();
        if (lstMarca.size() > 0) {
            Marca marcas = (Marca) queryDao.getResultList().iterator().next();
            return marcas;
        } else {
            queryDao = emMysql.createNamedQuery("Marca.findMaxId");
            Integer maxMarca = (Integer) queryDao.getSingleResult();
            Query query = emMysql.createNativeQuery("INSERT INTO marcas (CARMARK,Nombre_marca) " + " VALUES(?,?)");
            query.setParameter(1, maxMarca + 1);
            query.setParameter(2, marca);
            query.executeUpdate();
            emMysql.flush();
            Marca marcas = emMysql.find(Marca.class, maxMarca + 1);
            return marcas;

        }
    }

    private Color serFindColor(String strColor, EntityManager emMysql) {
        queryDao = emMysql.createNamedQuery("Color.findByNombrecolor");
        queryDao.setParameter("nombre", strColor);
        List<Color> lstColor = queryDao.getResultList();
        if (lstColor.size() > 0) {
            return (Color) lstColor.iterator().next();
        } else {
            queryDao = emMysql.createNamedQuery("Color.findMaxId");
            Integer maxColor = (Integer) queryDao.getSingleResult();
            Query query = emMysql.createNativeQuery("INSERT INTO colores (COLOR,Nombre_color) " + " VALUES(?,?)");
            query.setParameter(1, maxColor + 1);
            query.setParameter(2, strColor);
            query.executeUpdate();
            emMysql.flush();
            Color color = emMysql.find(Color.class, maxColor + 1);
            return color;
        }
    }

    private Vehiculo serFindVehiculo(String strPlaca, EntityManager emMysql) {
        queryDao = emMysql.createNamedQuery("Vehiculo.findByCarplate");
        queryDao.setParameter("placa", strPlaca);
        List<Vehiculo> lstVehiculo = queryDao.getResultList();
        if (lstVehiculo.size() > 0) {
            Vehiculo veh = (Vehiculo) lstVehiculo.iterator().next();
            veh.setVelocidad(0);
            return veh;
        } else {
            Vehiculo VehiculoNew = new Vehiculo();
            VehiculoNew.setVelocidad(1);
            return VehiculoNew;
        }
    }

    private Propietario serFindPropietario(Long cedula, EntityManager emMysql) {
        queryDao = emMysql.createNamedQuery("Propietarios.findByIdentificacion");
        queryDao.setParameter("identificacion", cedula);
        List<Propietario> lstPropietario = queryDao.getResultList();
        if (lstPropietario.size() > 0) {
            return (Propietario) lstPropietario.iterator().next();
        } else {
            Propietario propietarioNew = new Propietario();
            propietarioNew.setId(1L);
            return propietarioNew;
        }
    }

    private Aseguradora serFindAseguradora(String strAseguradora, EntityManager emMysql) {
        queryDao = emMysql.createNamedQuery("Aseguradora.findByNombre");
        queryDao.setParameter("nombre", strAseguradora);
        List<Aseguradora> lstAseg = queryDao.getResultList();
        if (lstAseg.size() > 0) {
            return (Aseguradora) lstAseg.iterator().next();
        } else {
            Aseguradora aseguradoraNew = new Aseguradora();
            aseguradoraNew.setNombre(strAseguradora);
            queryDao = emMysql.createNamedQuery("Aseguradora.findmaxId");
            Integer maxAseg = (Integer) queryDao.getSingleResult();
            aseguradoraNew.setId(maxAseg + 1);
            aseguradoraNew.setCodigo(String.valueOf(maxAseg + 1));
            emMysql.persist(aseguradoraNew);
            return aseguradoraNew;
        }

    }

    private Llanta serFindLlanta(String strLlanta, EntityManager emMysql) {
        queryDao = emMysql.createNamedQuery("Llanta.findByNombrellanta");
        queryDao.setParameter("nombrellanta", strLlanta);
        Llanta llanta = (Llanta) queryDao.getResultList().iterator().next();
        return llanta;
    }

    private HojaPruebas servFindConsecutivoHoja(HojaPruebas hp, EntityManager emMysql) {
        Integer maxCon;
        if (hp.getPreventiva().equalsIgnoreCase("N")) {
            queryDao = emMysql.createNamedQuery("HojaPruebas.findMaxConseRTM");
            maxCon = (Integer) queryDao.getSingleResult();
            if (maxCon == null) {
                maxCon = 0;
            }
            hp.setCon_hoja_prueba(maxCon + 1);
            hp.setCon_preventiva(0);
        } else {
            queryDao = emMysql.createNamedQuery("HojaPruebas.findMaxConsePrevent");
            maxCon = (Integer) queryDao.getSingleResult();
            if (maxCon == null) {
                maxCon = 0;
            }
            hp.setCon_preventiva(maxCon + 1);
            hp.setCon_hoja_prueba(0);
        }
        return hp;
    }

    private Integer servFindMaxIdHP(EntityManager emMysql) {
        queryDao = emMysql.createNamedQuery("HojaPruebas.findMaxHP");
        Integer maxHP = (Integer) queryDao.getSingleResult();
        if (maxHP == null) {
            maxHP = 1;
        }
        return (maxHP + 1);
    }

    private UsuarioMySql servFindUsuario(Integer cedula, EntityManager emMysql) {
        queryDao = emMysql.createNamedQuery("UsuarioMySql.findByCedula");
        queryDao.setParameter("cedula", cedula.toString());
        UsuarioMySql usuario = (UsuarioMySql) queryDao.getSingleResult();
        return usuario;
    }

    @Override
    public String servFindClasesVehiculoAct(EntityManager emMysql) {
        String respClase = "";
        queryDao = emMysql.createNamedQuery("ClaseVehiculo.findByClasesActivas");
        List<ClaseVehiculo> lstClaseVeh = queryDao.getResultList();
        for (ClaseVehiculo claseVehiculo : lstClaseVeh) {
            respClase = respClase.concat(String.valueOf(claseVehiculo.getId()).concat(";").concat(claseVehiculo.getNombre()).concat("]"));
        }
        return respClase;
    }

    @Override
    public String setPanelConfiguracionCda(String strAtributos, EntityManager emMysql) {
        Cda cda = emMysql.find(Cda.class, 1);
        StringTokenizer flujo = new StringTokenizer(strAtributos, ";");
        cda.setResponsable(Integer.parseInt(flujo.nextToken()));
        cda.setUrlServicioSicov(flujo.nextToken());
        emMysql.merge(cda);
        return "1";
    }

    @Override
    public String servFindCtxCDA(EntityManager emMysql) {
        Cda cda = emMysql.find(Cda.class, 1);
        String respCDA = cda.getNombre().concat(";").concat(cda.getNit()).concat(";").concat(String.valueOf(cda.getResponsable())).concat(";").concat(cda.getCiudad()).concat(";").concat(cda.getProveedorSicov()).concat(";").concat(cda.getUrlServicioSicov()).concat(";").concat(cda.getOrdenCodigo()).concat(";").concat(cda.getOrdenVersion()).concat(";").concat(cda.getOrdenFecha()).concat(";").concat(cda.getOrdenBienvenido()).concat(";").concat(cda.getOrdenContrato().concat(";"));
        String dierectoresTec = "";
        queryDao = emMysql.createNamedQuery("Usuario.findDTActivos");
        List<UsuarioMySql> lstUsuario = queryDao.getResultList();
        for (UsuarioMySql usuario : lstUsuario) {
            dierectoresTec = dierectoresTec.concat(usuario.getCedula().toString()).concat(";").concat(usuario.getNombre()).concat("|");
        }
        return respCDA.concat("%").concat(dierectoresTec);
    }


    @Override
    public String servFindHojaPruebasAbiertas(Integer ctxPista, String ctxRevision, Integer tipoRevision, EntityManager emMysql, EntityManager em) {
        String flujoEnvio = "";
        Calendar calenInic = Calendar.getInstance();
        Calendar calenFin = Calendar.getInstance();
		/*queryDao  = em.createNamedQuery("PreRevision.findHojaPend");		
		
		queryDao.setParameter("primeraVez",tipoRevision);*/
//		      0             1       2         3           4          5             6               7            8           9             10             11          
        //pr.noRevision,pr.placa,pr.esRTM,pr.primeraVez,pr.noTurno,r.servicio,r.claseVehiculo,pr.tipoVehiculo,r.nroEjes,pr.esEnsenanza,r.poseePacha,pr.nacionalidad
        queryDao = emMysql.createNamedQuery("HojaPruebas.findHojaPruebaActiva");
        queryDao.setParameter("isRTM", ctxRevision);
        queryDao.setParameter("tipoRevision", tipoRevision);
        queryDao.setParameter("ctxPista", ctxPista);

        Boolean esRTM;
        Boolean esEns;
        String preven;
        Integer esEnse;
        List<Object[]> lstScalar = queryDao.getResultList();
        for (Object[] result : lstScalar) {
            preven = (String) result[2];
            if (preven.equalsIgnoreCase("Y")) {
                esRTM = false;
            } else {
                esRTM = true;
            }
            esEnse = (Integer) result[9];
            if (esEnse == 0) {
                esEns = false;
            } else {
                esEns = true;
            }

            flujoEnvio = flujoEnvio.concat(String.valueOf((Integer) result[0])).concat(";").concat((String) result[1]).concat(";").concat(esRTM.toString()).concat(";").concat(String.valueOf(String.valueOf((Integer) result[3]))).concat(";").concat(String.valueOf((Integer) result[4])).concat(";").concat((String) result[5]).concat(";").concat((String) result[6]).concat(";").concat((String) result[7]).concat(";").concat(String.valueOf((Integer) result[8])).concat(";").concat(String.valueOf(esEns)).concat(";").concat(String.valueOf(esEns)).concat(";").concat((String) result[10]).concat("|");

        }
        return flujoEnvio;
    }


    @Override
    public String servFindStatusPruebas(String testSheet, EntityManager emMysql, EntityManager em) {
        String flujoEnvio = "";
        String coment;
        HojaPruebas hojaPruebas = emMysql.find(HojaPruebas.class, Integer.parseInt(testSheet));
        if (hojaPruebas.getReinspeccionList().size() == 0) {
            List<Prueba> lstPruebas = hojaPruebas.getListPruebas();
            for (Prueba prueba : lstPruebas) {
                if (prueba.getObservaciones() == null) {
                    coment = "";
                } else {
                    coment = prueba.getObservaciones();
                }
                flujoEnvio = flujoEnvio.concat(prueba.getTipoPrueba().getNombre().concat(";")).concat(prueba.getFinalizada()).concat(";").concat(coment).concat(";").concat(prueba.getId().toString()).concat(";").concat(prueba.getTipoPrueba().getId().toString()).concat("|");
            }
        } else {
            Reinspeccion reinspeccion = hojaPruebas.getReinspeccionList().iterator().next();
            List<Prueba> lstPruebas = reinspeccion.getPruebaList();
            for (Prueba prueba : lstPruebas) {
                if (prueba.getObservaciones() == null) {
                    coment = "";
                } else {
                    coment = prueba.getObservaciones();
                }
                flujoEnvio = flujoEnvio.concat(prueba.getTipoPrueba().getNombre().concat(";")).concat(prueba.getFinalizada()).concat(";").concat(coment).concat(";").concat(prueba.getId().toString()).concat(";").concat(prueba.getTipoPrueba().getId().toString()).concat("|");
            }
        }
        return flujoEnvio;
    }

    @Override
    public String servFindExisRevision(String placa, EntityManager emMysql, EntityManager em) {
        Calendar calenInic = Calendar.getInstance();
        Calendar calenFin = Calendar.getInstance();
        LocalDate date = calenInic.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        TemporalUnit unidadTemporal = ChronoUnit.DAYS;
        LocalDate dateResultado = date.minus(14, unidadTemporal);
        calenInic.setTime(Date.from(dateResultado.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        queryDao = emMysql.createNamedQuery("HojaPruebas.findVigenciaRevision");
        queryDao.setParameter("placa", placa.toUpperCase().trim());
        queryDao.setParameter("fechaInicial", calenInic.getTime());
        queryDao.setParameter("fechaFinal", calenFin.getTime());
        List<Object[]> lstHp = queryDao.getResultList();
        int eve = lstHp.size();
        if (eve == 0) {
            dateResultado = date.minus(31, unidadTemporal);
            calenInic.setTime(Date.from(dateResultado.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            queryDao = emMysql.createNamedQuery("HojaPruebas.findVigenciaRevision");
            queryDao.setParameter("placa", placa.toUpperCase().trim());
            queryDao.setParameter("fechaInicial", calenInic.getTime());
            queryDao.setParameter("fechaFinal", calenFin.getTime());
            lstHp = queryDao.getResultList();
            eve = lstHp.size();
            if (eve > 0) {
                Object[] result = lstHp.iterator().next();
                String preventiva = (String) result[0];
                Integer fkRevision = (Integer) result[1];
                return "3;".concat(preventiva).concat(";").concat(fkRevision.toString());
            } else {
                return "0;0;0";
            }
        } else {
            Object[] result = lstHp.iterator().next();
            String preventiva = (String) result[0];
            Integer fkRevision = (Integer) result[1];
            return "1;".concat(preventiva).concat(";").concat(fkRevision.toString());
        }
    }

    @Override
    public String servFindDepartametos(EntityManager emMysql) {
        queryDao = emMysql.createNamedQuery("Departamento.findAll");
        List<Departamento> lstDepartamentos = queryDao.getResultList();
        String flujoDepartamento = "";
        for (Departamento departamento : lstDepartamentos) {
            flujoDepartamento = flujoDepartamento.concat(departamento.getId().toString()).concat(";").concat(departamento.getNombre()).concat("]");
        }
        return flujoDepartamento;
    }

    @Override
    public String servFindCiudades(Integer identCiudad, EntityManager emMysql) {

        queryDao = emMysql.createNamedQuery("Ciudad.findByIdDepartamento");
        queryDao.setParameter("idDepartamento", identCiudad);
        List<Ciudad> lstCiudad = queryDao.getResultList();
        String flujoCiudad = "";
        for (Ciudad ciudad : lstCiudad) {
            flujoCiudad = flujoCiudad.concat(ciudad.getId().toString()).concat(";").concat(ciudad.getNombre()).concat("]");
        }
        return flujoCiudad;
    }
}
