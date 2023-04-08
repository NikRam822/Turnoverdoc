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

  getOrders() {
    return this.http.get("http://localhost:8080/api/order/get/all", {
      observe: 'response',
      withCredentials: true
    })
  }

/*  postCreateOrder(phone: String, messenger: String) {
    const body = {
     phone: phone,
     messenger: messenger
    };
    return this.http.post("http://localhost:8080/api/order/createOrder?phone="+phone+"&messenger="+messenger,body,{
      observe: 'response',
      withCredentials: true
    })
  }*/

  postCreateOrder(phone: String, messenger: String) {
    const body = {
      phone: phone,
      messenger: messenger
    };
    return this.http.post("http://localhost:8080/api/order/createOrder",body,{
      observe: 'response',
      withCredentials: true
    })
  }

  getCalls(sortData: string[], idFilter: string) {
    return this.http.get("http://localhost:8080/api/calls?sort=" + sortData[1] + "&order=" + sortData[0] + "&id=" + idFilter, {
      observe: 'response',
      withCredentials: true
    })
  }

  postAddUser(id: Number | undefined, name: string, phone: string, archive: string, password: string, login: string, language: string) {
    const body = {
      id: id,
      name: name,
      phone: phone,
      archive: archive,
      password: password,
      login: login,
      language: language
    };
    return this.http.post('http://localhost:8080/api/add/user', body, {
      observe: 'response',
      withCredentials: true
    });

  }
}
