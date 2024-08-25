add dependency oauth2 resource server to pom.xml
create a new class named SecurityConfig in the package org.sid.ebankingbackend.security
create a methode for return a bean of type PasswordEncoder
create a methode InMemoryUserDetailsManager 
create a methode SecurityFilterChain 
la security basique nessécite a envouyer un header Authorization avec la valeur Basic suivi par le nom d'utilisateur et le mot de passe encodé en base64 (en peut faire dans intellegiJ idea Tools -> Http Client -> Create Request)  
donc la reson pour laquelle on va utiliser auth with jwt c'est pour eviter d'envoyer le mot de passe a chaque fois
pour utiliser jwt voila les etapes:
  ajouter ca .oauth2ResourceServer(oa->oa.jwt(Customizer.withDefaults())) // Use JWT dans la methode configure(HttpSecurity http) de la classe SecurityConfig
  create 2 beans JwtEncoder et JwtDecoder dans la classe SecurityConfig pour encoder et decoder le token
  ajouter la cle secrete dans application.properties et injecter la dans la classe SecurityConfig
  ajouter une endpoint pour ahthentifier l'utilisateur (l'utilisateur doit envoyer un requete POST avec le username et le password dans le body et le serveur va retourner un token jwt a l'utilisateur) dans la classe SecurityController

apres ca quand l'utilisateur veut acceder a une ressource protegé il doit envoyer un header Authorization avec la valeur Bearer suivi par le token jwt

et apres ca on va securiser les endpoints de l'api en ajoutant les annotations @PreAuthorize("hasRole('ROLE_USER')") ou @PreAuthorize("hasRole('ROLE_ADMIN')") sur les methodes des classes des controllers
