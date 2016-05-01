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

public class Test {
	
	  public static void main(String[] args) {
		    ClientConfig config = new ClientConfig();
		    Client client = ClientBuilder.newClient(config);
		    WebTarget target = client.target(getBaseURI());
	
		    // Crear un usuario nuevo
		    Usuario usuario = new Usuario("Usuario_Client");
		    Response response = target.path("usuarios").request()
		    		.accept(MediaType.TEXT_HTML)
		    		.post(Entity.xml(usuario),Response.class);
		    System.out.println("Create new user: " + response.getStatus());
			if(response.getHeaders().containsKey("Location")) {
				Object location = response.getHeaders().get("Location").get(0);
				System.out.println("Create new user: " + location.toString());
			}  
		    
		    // Publicar un post nuevo (o varios)
		    Post post = new Post("Post_Client");
		    post.setUserId(1);
		    response = target.path("usuarios")
		    		.path("1").path("posts")
		    		.request().accept(MediaType.TEXT_HTML)
		    		.post(Entity.xml(post),Response.class);
		    System.out.println("Publish new post: " + response.getStatus());
			if(response.getHeaders().containsKey("Location")) {
				Object location = response.getHeaders().get("Location").get(0);
				System.out.println("Publish new post: " + location.toString());
			}  
		    
		    // Obtener mis posts usando los filtros disponibles 
		    // (en este caso, el cliente debe poder optar  por  obtener  
		    // la  lista  de  identificadores  de  esos  posts  o  bien 
		    // directamente el contenido de dichos posts)
			System.out.println("Get my posts: " + 
					target.path("usuarios").path("1").path("posts")
						.queryParam("startDate", "2016-04-27")
						.queryParam("endDate", "2016-04-28")
						.queryParam("start", "0")
						.queryParam("end", "4")
						.request()
			        .accept(MediaType.APPLICATION_XML).get(String.class)
			        );
			
		    
		    // Modificar un post
		    post = new Post("Post_Client_Modificado");
		    post.setId(4);
		    post.setDate("2016-05-02");
		    post.setUserId(1);
		    response = target.path("usuarios")
		    		.path("1").path("posts").path(String.valueOf(post.getId()))
		    		.request().accept(MediaType.APPLICATION_XML)
		    		.put(Entity.xml(post),Response.class);
		    System.out.println("Modify post: " + response.getStatus());
			if(response.getHeaders().containsKey("Location")) {
				Object location = response.getHeaders().get("Location").get(0);
				System.out.println("Modify post: " + location.toString());
			}  
		    
		    // Borrar un post 
		    response = target.path("usuarios")
		    		.path("1").path("posts").path("2")
		    		.request().delete();
		    System.out.println("Delete post: " + response.getStatus());
		    
		    // Buscar posibles amigos entre los usuarios
		    System.out.println("Get possible amigos: " + 
					target.path("usuarios").path("1").path("posiblesAmigos")
						.request()
			        .accept(MediaType.APPLICATION_XML).get(String.class)
			        );
		    
		    // Agregar un amigo
		    Usuario amigo = usuario;
		    amigo.setId(5);
		    response = target.path("usuarios").path("1").path("amigos")
		    		.request()
		    		.accept(MediaType.TEXT_HTML)
		    		.post(Entity.xml(amigo),Response.class);
		    System.out.println("Added new amigo: " + response.getStatus());
			if(response.getHeaders().containsKey("Location")) {
				Object location = response.getHeaders().get("Location").get(0);
				System.out.println("Added new amigo: " + location.toString());
			}  
		    
		    // Eliminar a un amigo
		    response = target.path("usuarios").path("1").path("amigos").path("5")
		    		.request().delete();
		    System.out.println("Delete amigo: " + response.getStatus());
		    
		    // Obtener la lista de amigos usando los filtros disponibles
		    System.out.println("Get my amigos: " + 
					target.path("usuarios").path("1").path("amigos")
						.queryParam("name", "u2")
						.queryParam("start", "0")
						.queryParam("end", "4")
						.request()
			        .accept(MediaType.APPLICATION_XML).get(String.class)
			        );
		    
		    // Consultar número de posts publicados por mí en un periodo
		    System.out.println("Get number of my posts published in a period: " + 
					target.path("usuarios").path("1").path("posts").path("count")
						.queryParam("startDate", "2016-04-27")
						.queryParam("endDate", "2016-04-28")
						.request()
			        .accept(MediaType.TEXT_HTML).get(String.class)
			        );
		    
		    // Obtener la lista de usuarios
		    System.out.println("Get users: " + target.path("usuarios").request()
		        .accept(MediaType.APPLICATION_XML).get(String.class));

		    // Modificar los datos de nuestro perfil
		    usuario = new Usuario("Usuario_Client_Modificado!");
		    usuario.setId(1);
		    response = target.path("usuarios")
		    	.path("1").request().accept(MediaType.APPLICATION_XML)
		    	.put(Entity.xml(usuario),Response.class);
		    System.out.println("Modify our profile: " + response.getStatus());
		    
		    // Darse de baja de la redsocial
		    response = target.path("usuarios").path("1")
		    		.request().delete();
		    System.out.println("Delete post: " + response.getStatus());
		    
		    // Obtener la lista de posts publicados por amigos que contienen un determinadotexto
		    System.out.println("Get posts of my amigos: " + 
					target.path("usuarios").path("1").path("amigos").path("posts")
						.queryParam("dt", "2016-04-30")
						.queryParam("content", "p")
						.queryParam("start", "0")
						.queryParam("end", "4")
						.request()
			        .accept(MediaType.APPLICATION_XML).get(String.class)
			        );
	  }

	  private static URI getBaseURI() {
	    return UriBuilder.fromUri("http://localhost:8080/UPMsocial/").build();
	  }
} 