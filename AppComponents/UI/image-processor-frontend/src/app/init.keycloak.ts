import { KeycloakService } from 'keycloak-angular';
import { environment } from '../environments/environment';


export function initKeycloak (keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://' + environment.keycloakServiceName + ':8080',
        realm: 'ImageProcessor',
        clientId: 'image-processor-frontend',
      },
      initOptions: {
        onLoad: 'check-sso',
        checkLoginIframe: false
      },
      enableBearerInterceptor: true,
      bearerPrefix: 'Bearer',
    });
}