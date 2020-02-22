<template>
  <el-container>
    <!-- 头部区域 -->
    <el-header>Sky2010一卡通管理系统实时监控</el-header>
    <!-- 主内容区域 -->
    <el-main>
      <!-- Tabs 分页区 -->
      <el-tabs type="border-card">
        <!-- 访问事件 -->
        <el-tab-pane>
          <span slot="label"><i class="el-icon-date"></i> 访问事件</span>
          <el-row>
            <el-col :span="4">
              <img src="" alt="人员照片" />
            </el-col>
            <el-col :span="20">
              <!-- 访问事件表格显示 -->
              <el-table
                :data="accessList"
                border
                stripe
                :row-style="accessTableRowStyle"
              >
                <!-- :row-key="accessRowKey"
                :expand-row-keys="accessExpandRowKeys" -->

                <el-table-column type="expand" v-if="accessList.length > 0">
                  <template slot-scope="props">
                    <el-form label-position="left" class="demo-table-expand">
                      <el-form-item label="时间：">
                        <span>{{ props.row.eventDateTime }}</span>
                      </el-form-item>
                      <el-form-item label="部门：">
                        <span>{{ props.row.departmentName }}</span>
                      </el-form-item>
                      <el-form-item label="人员号：">
                        <span>{{ props.row.personNo }}</span>
                      </el-form-item>
                      <el-form-item label="卡号：">
                        <span>{{ props.row.cardId }}</span>
                      </el-form-item>
                      <el-form-item label="名称：">
                        <span>{{ props.row.userName }}</span>
                      </el-form-item>
                      <el-form-item label="设备：">
                        <span>{{ props.row.deviceName }}</span>
                      </el-form-item>
                      <el-form-item label="事件：">
                        <span>{{ props.row.eventName }}</span>
                      </el-form-item>
                      <el-form-item label="描述：">
                        <span>{{ props.row.memo }}</span>
                      </el-form-item>
                    </el-form>
                  </template>
                </el-table-column>
                <el-table-column type="index" label="#"></el-table-column>
                <el-table-column
                  label="时间"
                  prop="eventDateTime"
                ></el-table-column>
                <el-table-column label="人员" prop="personNo"></el-table-column>
                <el-table-column label="名称" prop="userName"></el-table-column>
                <el-table-column
                  label="设备"
                  prop="deviceName"
                ></el-table-column>
                <el-table-column
                  label="事件"
                  prop="eventName"
                ></el-table-column>
              </el-table>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="报警事件">
          <!-- 报警事件表格显示 -->
          <el-table
            :data="alarmList"
            border
            stripe
            :row-style="alarmTableRowStyle"
          >
            <el-table-column type="index" label="#"></el-table-column>
            <el-table-column
              label="时间"
              prop="eventDateTime"
            ></el-table-column>
            <el-table-column label="设备" prop="deviceName"></el-table-column>

            <el-table-column label="事件" prop="eventName"></el-table-column>
            <el-table-column label="描述" prop="memo"></el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="巡更事件">
          <!-- 巡更事件表格显示 -->
          <el-table
            :data="tourList"
            border
            stripe
            :row-style="tourTableRowStyle"
          >
            <el-table-column type="index" label="#"></el-table-column>
            <el-table-column
              label="时间"
              prop="eventDateTime"
            ></el-table-column>
            <el-table-column label="巡更路线" prop="tourLine"></el-table-column>
            <el-table-column label="设备" prop="deviceName"></el-table-column>
            <el-table-column label="巡更人员" prop="tourName"></el-table-column>
            <el-table-column label="事件" prop="eventName"></el-table-column>
            <el-table-column label="描述" prop="memo"></el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="日志事件">
          <!-- 日志事件表格显示 -->
          <el-table :data="logList" border stripe :row-style="logTableRowStyle">
            <el-table-column type="index" label="#"></el-table-column>
            <el-table-column
              label="时间"
              prop="eventDateTime"
            ></el-table-column>
            <el-table-column
              label="事件类型"
              prop="eventName"
            ></el-table-column>
            <el-table-column label="设备" prop="deviceName"></el-table-column>
            <el-table-column label="操作员" prop="userName"></el-table-column>
            <el-table-column label="计算机" prop="hostName"></el-table-column>
            <el-table-column label="描述" prop="memo"></el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>

<script>
// import util from '../utils/util'
export default {
  // 模块数据
  data() {
    return {
      // WebSocket实例
      socket: null,
      // WebSocket 服务器url
      url: 'ws://localhost:8080/SkyMonitor/websocket',

      // 信息显示行数
      rowSize: 50,
      // 访问事件数据
      accessList: [],
      accessExpandRowKeys: [],

      // 报警事件数据
      alarmList: [],
      // 日志事件数据
      logList: [],
      // 巡更事件数据
      tourList: []
    }
  },

  mounted() {
    this.socketConnect()
  },
  destroyed() {
    this.socket.close()
  },

  methods: {
    // 打开WebSocket 连接
    socketConnect() {
      this.socket = new WebSocket(this.url)
      // 连接侦听
      this.socket.addEventListener('open', this.socketOpen)
      // 断开连接侦听
      this.socket.addEventListener('close', this.socketClose)
      // 错误侦听
      this.socket.addEventListener('error', this.socketError)
      // 信息侦听
      this.socket.addEventListener('message', this.socketMessage)
    },
    // 服务器连接成功回调
    socketOpen(event) {
      console.log('open', 'WebSocket Server连接成功！')
      this.$message.success('连接服务器成功！')

      // 每个3秒向服务器发送消息包
      // setInterval(() => {
      //   this.wsmsg.accessMsg.recNum = new Date().getTime()
      //   this.wsmsg.accessMsg.eventDateTime = util.formatDateTime(
      //     'yyyy-MM-dd hh:mm:ss',
      //     new Date()
      //   )
      //   this.socket.send(JSON.stringify(this.wsmsg))
      // }, 1000)
    },
    // 服务器断开连接回调
    socketClose(event) {
      console.log('close', 'WebSocket Server服务器断开连接！')
      this.$message.success('服务器断开连接！')
    },
    // 连接失败回调
    socketError(event) {
      console.log('error', event)
      // 每10秒，尝试重新连接
      setTimeout(() => {
        this.socketConnect()
      }, 10000)
    },
    // 收到消息回调
    // {
    //   type: 'access', // 消息类型 access,alarm,tour,log
    //   event: {
    //     recNum: 0,
    //     color: '#ff0000',
    //     eventDateTime: util.formatDateTime('yyyy-MM-dd hh:mm:ss', new Date()),
    //     departmentName: '开发部',
    //     personNo: 'N12345',
    //     cardId: '12345',
    //     userName: '蔡民安',
    //     deviceName: '办公室大门',
    //     eventName: '合法授权',
    //     memo:
    //       '上传事件时间' +
    //       util.formatDateTime('yyyy-MM-dd hh:mm:ss', new Date())
    //   }
    // },
    socketMessage(event) {
      // console.log('message：', event.data)
      const data = JSON.parse(event.data)
      console.log('message：', data)
      // console.log(this.accessList)

      if (data.type === 'access') {
        if (this.accessList.length >= this.rowSize) {
          this.accessList.pop()
        }
        this.accessList.unshift(data.event)
        // this.accessExpandRowKeys = [data.accessMsg.recNum]
        //  console.log(this.accessList)
      } else if (data.type === 'alarm') {
        if (this.alarmList.length >= this.rowSize) {
          this.alarmList.pop()
        }
        this.alarmList.unshift(data.event)
      } else if (data.type === 'log') {
        if (this.logList.length >= this.rowSize) {
          this.logList.pop()
        }

        this.logList.unshift(data.event)
      } else if (data.type === 'tour') {
        if (this.tourList.length >= this.rowSize) {
          this.tourList.pop()
        }
        this.tourList.unshift(data.event)
      }
    },
    // 颜色转换
    changeColor(color) {
      // console.log('color', color)
      const b = ('00' + ((color & 0xff0000) >> 16).toString(16)).substr(-2)
      const g = ('00' + ((color & 0xff00) >> 8).toString(16)).substr(-2)
      const r = ('00' + (color & 0xff).toString(16)).substr(-2)
      // console.log('b=', b)
      // console.log('r=', r)
      // console.log('g=', g)
      return '#' + r + g + b
    },
    // 控制行字体颜色方法
    accessTableRowStyle(row) {
      // console.log(
      //   'changecolor',
      //   this.changeColor(this.accessList[row.rowIndex].color)
      // )
      if (this.accessList[row.rowIndex].color) {
        return { color: this.changeColor(this.accessList[row.rowIndex].color) }
      }
    },
    // 控制行字体颜色方法
    alarmTableRowStyle(row) {
      if (this.alarmList[row.rowIndex].color) {
        return { color: this.changeColor(this.alarmList[row.rowIndex].color) }
      }
    },
    // 控制行字体颜色方法
    tourTableRowStyle(row) {
      if (this.tourList[row.rowIndex].color) {
        return { color: this.changeColor(this.tourList[row.rowIndex].color) }
      }
    },
    // 控制行字体颜色方法
    logTableRowStyle(row) {
      if (this.logList[row.rowIndex].color) {
        return { color: this.changeColor(this.logList[row.rowIndex].color) }
      }
    }
    // accessRowKey(row) {
    //   return row.recNum
    // }
    // accessExpandChange(row, expandedRows) {
    //   console.log(expandedRows)
    // }
  }
}
</script>

<style lang="less" scoped>
.el-header {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 60px;
  font-size: 26px;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
}
.el-table {
  width: 100%;
  height: calc(100vh);
}

.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
}

.el-row img {
  width: 200px;
  height: 250px;
  background: #e9eef3;
  text-align: center;
}
</style>
