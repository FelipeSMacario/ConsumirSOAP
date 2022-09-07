package com.example.ConsumindoSpring.controller;

import com.example.ConsumindoSpring.servico.AtendeCliente;
import com.example.ConsumindoSpring.servico.EnderecoERP;
import com.example.ConsumindoSpring.servico.SQLException_Exception;
import com.example.ConsumindoSpring.servico.SigepClienteException;
import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.developer.WSBindingProvider;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping("teste")
public class CorreiosController {

    @GetMapping("correio/{CEP}")
    public String testeCorreio(@PathVariable String CEP) throws Exception{
        URL urlCooreios = new URL("https://apps.correios.com.br/SigepMasterJPA/AtendeClienteService/AtendeCliente?wsdl");
        QName qnameCorreios = new QName("http://cliente.bean.master.sigep.bsb.correios.com.br/", "AtendeClienteService");

        Service serviceCorreios = Service.create(urlCooreios, qnameCorreios);

        AtendeCliente correios = serviceCorreios.getPort(AtendeCliente.class);

        WSBindingProvider bp = (WSBindingProvider)correios;

//        bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "");
//        bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "");

        EnderecoERP endereco = correios.consultaCEP(CEP);
        return endereco.getCidade();
    }

    /*@RestController
@RequestMapping("/codes")
public class CodigosRestController {
    @GetMapping
    public String obtainCode(){
        CodesService_Service service = new CodesService_Service();
        CodesService port = service.getCodesServicePort();
        // generate the headers
        Map<String, List<String>> requestHeaders = new HashMap<>();
        requestHeaders.put("apiKey",Arrays.asList("TokeApi yourtokenhere"));
        BindingProvider bindingProvider = (BindingProvider) port;
        // set the headers on the request context
        bindingProvider.getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);

        String code = port.methodTwo().toString();
        return code;
    }*/
}
