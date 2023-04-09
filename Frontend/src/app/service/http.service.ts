import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class RequestService {

  constructor(private http: HttpClient) {
  }

  postAuth(login: String, password: String) {
    const body = {username: login, password: password};
    return this.http.post('http://localhost:8080/api/v1/auth/login', body, {
      observe: 'response',
      withCredentials: true
    });

  }

  postReg( email: String, firstName: String, password: String, secondName: String, lastName: String, login: String) {
    const body = {email: email, firstName: firstName, password: password, secondName: secondName, surname: lastName, username: login};
    return this.http.post('http://localhost:8080/api/v1/auth/registration', body, {
      observe: 'response',
      withCredentials: true
    });

  }

  getOrders() {
    return this.http.get("http://localhost:8080/api/order/get/all", {
      observe: 'response',
      withCredentials: true
    })
  }

  postCreateOrder(phone: String, messenger: String) {
    const body = {
     phone: phone,
     messenger: messenger
    };
    return this.http.post("http://localhost:8080/api/order/createOrder?phone="+phone+"&messenger="+messenger,body,{
      observe: 'response',
      withCredentials: true,
    })
  }

  postUploadDocs(id: Number | undefined){
    const body = {
      id: id,
    };
    return this.http.post('http://localhost:8080/api/order/uploadDocs/', body, {
      observe: 'response',
      withCredentials: true
    });

  }
}
