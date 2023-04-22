export interface Order {
  id: Number
  status: String
  date: number

  p45Path: String
  p60Path: String
  p80Path: String
  passportPath: String
  contractPath:String

}

export interface FullOrder {
  id: Number
  status: String
  date: number
  username:string

  p45Path: String
  p60Path: String
  p80Path: String
  passportPath: String
  contractPath:String
}
