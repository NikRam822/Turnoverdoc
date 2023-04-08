import { EntityState, EntityStore, StoreConfig } from '@datorama/akita';
import {Injectable} from "@angular/core";
import {Order} from "../models/orders";

export interface OrderState extends EntityState<Order|undefined> {
  order:Order|undefined
}
@Injectable({
  providedIn: 'root'
})
@StoreConfig({ name: 'order' })
export class OrderStore extends EntityStore<OrderState> {
  constructor() {
    super({ user: undefined} ) ;
  }
}
