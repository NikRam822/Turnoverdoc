import { EntityState, EntityStore, StoreConfig } from '@datorama/akita';
import {Injectable} from "@angular/core";
import {FullOrder, Order} from "../models/orders";

export interface OrderState extends EntityState<Order|undefined, FullOrder|undefined> {
  order:Order|undefined
  fullOrder: FullOrder|undefined
}
@Injectable({
  providedIn: 'root'
})
@StoreConfig({ name: 'order' })
export class OrderStore extends EntityStore<OrderState> {
  constructor() {
    super({ order: undefined, fullOrder:undefined} ) ;
  }
}
