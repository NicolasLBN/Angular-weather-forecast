import { Component } from '@angular/core';
import  firebase from 'firebase';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent {
  title = 'weather';

  constructor() {
    // Your web app's Firebase configuration
    var firebaseConfig = {
      apiKey: "AIzaSyCc0_UQa5vFw-Da0xXXXX-GlzCTtlPg998",
      authDomain: "weatherapp-5d41c.firebaseapp.com",
      databaseURL: "https://weatherapp-5d41c.firebaseio.com",
      projectId: "weatherapp-5d41c",
      storageBucket: "weatherapp-5d41c.appspot.com",
      messagingSenderId: "43392944695",
      appId: "1:43392944695:web:6d53e4a193a88b2eaf8db2",
      measurementId: "G-760N6KWHLS"
    };
    // Initialize Firebase
    firebase.initializeApp(firebaseConfig);
    firebase.analytics();

  }
}