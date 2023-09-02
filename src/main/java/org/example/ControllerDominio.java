package org.example;

// El controldor es un objeto que contiene todos los casos de uso del dominio e interactua con su DAO correspondiente
public class ControllerDominio {
    public void listar() {
        new DominioDAO().listar();
    }
}
