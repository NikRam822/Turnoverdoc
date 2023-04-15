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

    return this.http.post("http://localhost:8080/api/order/createOrder",body,{
      observe: 'response',
      withCredentials: true,

    })
  }

  postUploadDocs(id:Number|undefined,filesMap: Map<String,File>){


    const formData: FormData = new FormData();
    formData.append('CONTRACT',filesMap.get('CONTRACT'));
    formData.append('PASSPORT',filesMap.get('PASSPORT'));
    formData.append('P_45',filesMap.get('P_45'));
    formData.append('P_60',filesMap.get('P_60'));
    formData.append('P_80',filesMap.get('P_80'));
    /*const body = {
    };*/
    return this.http.post('http://localhost:8080/api/order/uploadDocs/'+id, formData, {
      observe: 'response',
      withCredentials: true
    });

  }
}
