### Implementasi Auth Server

Buat class extends ke AuthorizationServerConfigurerAdapter

```
@Override
	clients.inMemory()
		.withClient("aplikasiweb")
		.authorities("APLIKASI_CLIENT_OAUTH2")
		.secret("aplikasi123")
		.redirectUris("http://examples.com")
		.authorizedGrantTypes("authorization_code", "refresh_token")
		.scopes("modul_admin", "modul_pasien")
		;
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("hasAuthority('APLIKASI_CLIENT_OAUTH2')");
	}
```

Endpoint:

* authotization/ to get token: http://localhost:8000/oauth/authorize?response_type=code&client_id=aplikasiweb&redirect_url=http://example.com

setelah login hasilnya misalnya: https://www.examples.com/?code=VbDrVD

* Open Rest Client because they have basic auth to Change access code with access token

curl -X POST -vu clientauthcode:123456 http://localhost:8000/oauth/token -H "Accept: application/json" -d "grant_type=authorization_code&code=VbDrVD&redirect_uri=http://localhost:9000/api/current-user"

- Request URI: http://localhost:8000
- BODY --> Content Headers --> Content type: applications/x-www-form-urlencoded
- Request Payload: 
    - Request Parameter: 
        - code: VbDrVD
        
### Grant Type User Password

curl -X POST -vu adminapp:password http://localhost:8000/oauth/token -H "Accept: application/json" -d "client_id=adminapp&grant_type=password&username=admin&password=password"

