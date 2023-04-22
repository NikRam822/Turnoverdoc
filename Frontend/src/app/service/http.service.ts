import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';


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

  postReg(email: String, firstName: String, password: String, secondName: String, lastName: String, login: String) {
    const body = {
      email: email,
      firstName: firstName,
      password: password,
      secondName: secondName,
      surname: lastName,
      username: login
    };
    return this.http.post('http://localhost:8080/api/v1/auth/registration', body, {
      observe: 'response',
      withCredentials: true
    });

  }

  postAdminAuth(login: String, password: String) {
    const body = {username: login, password: password};
    return this.http.post('http://localhost:8080/api/v1/auth/admin/login', body, {
      observe: 'response',
      withCredentials: true
    });

  }

  postAdminFilterOrders(username: string, statusOfOrder: string) {
    let usernamePath = "";
    let statusOfOrderPath = "";

    if (username && statusOfOrder) {
      statusOfOrderPath = "?statusOfOrder=" + statusOfOrder
      usernamePath = "&username=" + username

    } else {
      if (statusOfOrder) {
        statusOfOrderPath = "?statusOfOrder=" + statusOfOrder
      } else {
        if (username) {
          usernamePath = "?username=" + username
        }
      }
    }
    let queryParams = new HttpParams();
    queryParams.append("statusOfOrder", statusOfOrder);
    queryParams.append("username", username);


    return this.http.post('http://localhost:8080/api/v1/admin/order/get-filtered-orders' + statusOfOrderPath + usernamePath, null, {
      observe: 'response',
      withCredentials: true,
      params: queryParams
    });

  }

  postAdminChangeStatus(orderId: string, status: string) {

    let queryParams = new HttpParams();
    queryParams.append("orderId", orderId);
    queryParams.append("status", status);
    return this.http.post("http://localhost:8080/api/v1/admin/changeStatus?orderId=" + orderId + "&status=" + status, null, {
      observe: 'response',
      withCredentials: true,
      params: queryParams
    })
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

    return this.http.post("http://localhost:8080/api/order/createOrder", body, {
      observe: 'response',
      withCredentials: true,

    })
  }

  postUploadDocs(id: Number | undefined, filesMap: Map<String, File>) {


    const formData: FormData = new FormData();
    formData.append('CONTRACT', filesMap.get('CONTRACT'));
    formData.append('PASSPORT', filesMap.get('PASSPORT'));
    formData.append('P_45', filesMap.get('P_45'));
    formData.append('P_60', filesMap.get('P_60'));
    formData.append('P_80', filesMap.get('P_80'));

    return this.http.post('http://localhost:8080/api/order/uploadDocs/' + id, formData, {
      observe: 'response',
      withCredentials: true
    });

  }

  getDownloadDocs(id: Number, fileName: String) {
    return this.http.get("http://localhost:8080/api/order/download/" + id + "/" + fileName, {
      observe: 'response',
      withCredentials: true
    })
  }

  postConfirmDocs(orderId: Number) {
    let queryParams = new HttpParams();
    queryParams.append("orderId", String(orderId));
    return this.http.post("http://localhost:8080/api/order/confirmDocs/" + orderId, null, {
      observe: 'response',
      withCredentials: true,
      params:queryParams
    })
  }
}
