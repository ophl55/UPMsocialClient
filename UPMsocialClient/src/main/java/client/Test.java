package client;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientConfig;
//import javax.ws.rs.client.config.DefaultClientConfig;
//import javax.ws.rs.representation.Form;

import model.Usuario;
import model.Post;

import java.util.Date;

public class Test {
	
	  public static void main(String[] args) {
		    ClientConfig config = new ClientConfig();
		    Client client = ClientBuilder.newClient(config);
		    WebTarget target = client.target(getBaseURI());
	
		    // Crear un usuario nuevo
		    Usuario usuario = new Usuario("Blabla");
		    usuario.setId(3);
		    Response response = target.path("usuarios").request()
		    		.accept(MediaType.TEXT_HTML)
		    		.post(Entity.xml(usuario),Response.class);
		    System.out.println("Create new user: " + response.getStatus());
			if(response.getHeaders().containsKey("Location")) {
				Object location = response.getHeaders().get("Location").get(0);
				System.out.println("Create new user: " + location.toString());
			}  
		    
		    // Publicar un post nuevo (o varios)
		    Post post = new Post("Post Blabla");
		    post.setDate(new Date());
		    post.setUserId(0);
		    response = target.path("usuarios")
		    		.path("0").path("posts")
		    		.request().accept(MediaType.TEXT_HTML)
		    		.post(Entity.xml(post),Response.class);
		    System.out.println("Publish new post: " + response.getStatus());
			if(response.getHeaders().containsKey("Location")) {
				Object location = response.getHeaders().get("Location").get(0);
				System.out.println("Publish new post: " + location.toString());
			}  
		    
		    
		    // TODO: Obtener mis posts usando los filtros disponibles 
		    // (en este caso, el cliente debe poder optar  por  obtener  
		    // la  lista  de  identificadores  de  esos  posts  o  bien 
		    // directamente el contenido de dichos posts)
		    
		    // Modificar un post
		    post = new Post("Post Blubb");
		    response = target.path("usuarios")
		    		.path("0").path("posts").path(String.valueOf(post.getId()))
		    		.request().accept(MediaType.APPLICATION_XML)
		    		.put(Entity.xml(post),Response.class);
		    System.out.println("Modify post: " + response.getStatus());
			if(response.getHeaders().containsKey("Location")) {
				Object location = response.getHeaders().get("Location").get(0);
				System.out.println("Modify post: " + location.toString());
			}  
		    
		    // Borrar un post 
		    response = target.path("usuarios")
		    		.path("0").path("posts").path(String.valueOf(post.getId()))
		    		.request().delete();
		    System.out.println("Delete post: " + response.getStatus());
		    
		    // TODO: Buscar posibles amigos entre los usuarios
		    
		    // TODO: Agregar un amigo
		    // nur die usuarioID senden?
		    Usuario amigo = new Usuario("amigo_1");
		    
		    // TODO: Eliminar a un amigo
		    response = target.path("usuarios")
		    		.path(String.valueOf(usuario.getId())).path("amigos")
		    		.path(String.valueOf(amigo.getId()))
		    		.request().delete();
		    System.out.println("Delete amigo: " + response.getStatus());
		    
		    // TODO: Obtener la lista de amigos usando los filtros disponibles
		    
		    // TODO: Consultar número de posts publicados por mí en un periodo
		    /*response = target.path("usuarios")
		    		.path(usuario.getId()).path("posts").path("count") // es fehlt der Filter für die Periode
		    		.request().accept(MediaType.APPLICATION_XML)
		    		.get(String.class);
		    */
		    // Obtener la lista de usuarios
		    System.out.println("Get users: " + target.path("usuarios").request()
		        .accept(MediaType.APPLICATION_XML).get(String.class));

		    // TODO: Modificar los datos de nuestro perfil
		    usuario = new Usuario("Hostia, que bien funciona!");
		    usuario.setId(1);
		    response = target.path("usuarios")
		    	.path("1").request().accept(MediaType.APPLICATION_XML)
		    	.put(Entity.xml(usuario),Response.class);
		    System.out.println("Modify our profile: " + response.getStatus());
		    
		    // TODO: Darse de baja de la redsocial
		    
		    // TODO: Obtener la lista de posts publicados por amigos que contienen un determinadotexto
		    
	  }

	  private static URI getBaseURI() {
	    return UriBuilder.fromUri("http://localhost:8080/UPMsocial/").build();
	  }
} 