import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { JwtComponent } from './jwt/jwt.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, JwtComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'image-processor-ui';
}
