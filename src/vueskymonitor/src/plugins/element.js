import Vue from 'vue'
import {
  Button,
  Container,
  Main,
  Header,
  Table,
  TableColumn,
  Form,
  FormItem,
  Message,
  MessageBox,
  Tabs,
  TabPane,
  Row,
  Col,
  Card
} from 'element-ui'

Vue.use(Button)
Vue.use(Container)
Vue.use(Main)
Vue.use(Header)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Tabs)
Vue.use(TabPane)
Vue.use(Row)
Vue.use(Col)
Vue.use(Card)
Vue.prototype.$message = Message
Vue.prototype.$confirm = MessageBox.confirm
