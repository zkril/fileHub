<template>
  <a-layout>
    <a-layout-sider width="200" style="background: #fff">
      <a-menu
          mode="inline"
          :style="{ height: '100%', borderRight: 0 }"
      >
        <a-menu-item key="/admin/message" >
          <router-link to="/admin/message">作业管理</router-link>
        </a-menu-item>
        <a-menu-item key="/admin/course" >
          <router-link to="/admin/course">课程管理</router-link>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <div>
        <h1>已经发布的作业</h1>
        <p>
          <a-button type="primary" size="large" @click="add()">
            发布作业
          </a-button>
        </p>
        <a-table :columns="columns"
                 :data-source="messages">
        <template #action="{record}">
          <a-space size="small">
            <a-button type="primary" @click="edit(record)">
              编辑
            </a-button>
            <a-button type="primary" @click="show(record)">
              详情
            </a-button>
            <a-button type="danger" @click="handledelete(record.messageid)">
              删除
            </a-button>
            <a-button type="primary" @click="down(record.messageid)">
              下载作业
            </a-button>
          </a-space>
        </template>
        </a-table>
      </div>
    </a-layout-content>
  </a-layout>
  <a-modal
      title="修改作业信息"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
    <a-form :model="editmes" :label-col="{span:6}" :wrapper-col="{span:18}">
      <a-form-item label="作业编号">
        <a-input v-model:value="editmes.mid"/>
      </a-form-item>
      <a-form-item label="作业名称">
        <a-input v-model:value="editmes.message_name"/>
      </a-form-item>
      <a-form-item label="具体内容">
        <a-textarea v-model:value="editmes.message_body"
                    :auto-size="{ minRows: 3, maxRows: 5 }"/>
      </a-form-item>
      <a-form-item label="原截止时间">
        <a-input v-model:value="editmes.oldddl" readonly/>
      </a-form-item>
      <a-form-item label="新截止时间">
        <a-date-picker
            format="YYYY-MM-DD HH:mm:ss"
            :showTime="{ initialValue: moment('00:00:00', 'HH:mm:ss') }"
            placeholder="请选择时间"
            @change="timepick2" />
      </a-form-item>
    </a-form>
  </a-modal>
  <a-modal
      title="作业详情"
      v-model:visible="modalVisible2"
      :confirm-loading="modalLoading2"
      @ok="handleModalOk2"
  >
    <a-form :model="mes" :label-col="{span:6}" :wrapper-col="{span:18}">
      <a-form-item label="作业编号">
        <a-input v-model:value="mes.mid" readonly/>
      </a-form-item>
      <a-form-item label="作业名称">
        <a-input v-model:value="mes.message_name" readonly/>
      </a-form-item>
      <a-form-item label="所属课程">
        <a-input v-model:value="mes.courseName" readonly/>
      </a-form-item>
      <a-form-item label="具体内容">
        <a-textarea v-model:value="mes.message_body"
                    :auto-size="{ minRows: 3, maxRows: 5 }" readonly/>
      </a-form-item>
      <a-form-item label="截止时间">
        <a-input v-model:value="mes.ddl" readonly/>
      </a-form-item>
    </a-form>
  </a-modal>
  <a-modal
      title="发布作业"
      v-model:visible="modalVisible3"
      :confirm-loading="modalLoading3"
      @ok="handleModalOk3"
  >
    <a-form :model="addmes" :label-col="{span:6}" :wrapper-col="{span:18}" >
      <a-form-item label="课程名称">
        <a-select :showSearch="true"  placeholder="选择学委对应的课程" @change="getcourseid">
          <a-select-option v-for="i in course" :key="i.courseid" :value="i.courseid">
            {{i.courseName}}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="作业编号" :rules="[{required:true,message: '请输入内容!',}]">
        <a-input v-model:value="addmes.mid"/>
      </a-form-item>
      <a-form-item label="作业名称" :rules="[{required:true}]">
        <a-input v-model:value="addmes.message_name"/>
      </a-form-item>
      <a-form-item label="具体内容">
        <a-textarea v-model:value="addmes.message_body"
                    :auto-size="{ minRows: 3, maxRows: 5 }"/>
      </a-form-item>
      <a-form-item label="截止时间" >
        <a-date-picker
            format="YYYY-MM-DD HH:mm:ss"
            :showTime="{ initialValue: moment('00:00:00', 'HH:mm:ss') }"
            placeholder="请选择时间"
            @change="timepick" />
      </a-form-item>
    </a-form>
  </a-modal>
  <a-modal
      title="下载作业"
      v-model:visible="modalVisible4"
      :confirm-loading="modalLoading4"
      @ok="handleModalOk4"
  >
    <a-table :columns="filecolumns"
             :data-source="filelist">
      <template #action="{record}">
        <a-space size="small">
          <a-button type="primary" @click="downloadfile(record.fileid)">
            下载该作业
          </a-button>
        </a-space>
      </template>
    </a-table>



  </a-modal>
</template>

<script lang="ts">
import {defineComponent, ref, onMounted, computed} from 'vue';
import Axios from "axios";
import store from "@/store";
import {Tool} from "@/util/tool";
import moment from "moment";
import "moment/locale/zh-cn"
import {message} from "ant-design-vue";

export default defineComponent({
  name:'AdminMessage',
  setup(){
    const modalVisible4=ref(false);
    const modalLoading4=ref(false);
    const filelist=ref();

    const down=(messageid:any)=>{
      modalVisible4.value=true;
      Axios.get("/message/file/search", {
        params:{
          messageid:messageid,
        }
      }).then((response)=>{
        const data=response.data;
        filelist.value=data.data;
      })
    };

    const downloadfile=(fileid:any)=>{
          Axios.get('/message/file/download', {
            params: {fileid: fileid,},responseType: 'blob'})
          .then((res) => {
             console.log(res);
            //console.log(res.data); // 获取服务端提供的数据
            let blob = new Blob([res.data])
            console.log(res.data)
            let contentDisposition = res.headers['content-disposition']
            let pattern = new RegExp('filename=([^;]+\\.[^.;]+);*')
            let result = pattern.exec(contentDisposition)
            // 使用decodeURI对名字进行解码
            let fileName: string;
            if(result) {
              fileName = decodeURI(result[1])

              let downloadElement = document.createElement('a')
              // 创建下载的链接
              let href = window.URL.createObjectURL(blob)
              downloadElement.style.display = 'none'
              downloadElement.href = href


              // 下载后文件名
              downloadElement.download = fileName
              document.body.appendChild(downloadElement)
              // 点击下载
              downloadElement.click()
              // 下载完成移除元素
              document.body.removeChild(downloadElement)
              // 释放掉blob对象
              window.URL.revokeObjectURL(href)
            }
          })
          .catch(() => {
            alert("请求出错");
          })
    };

    const handleModalOk4=()=>{
      modalLoading4.value=true;
      setTimeout(()=>{
        modalLoading4.value=false;
        modalVisible4.value=false;
      },100)
    };

    const filecolumns = [
      {
        title: '文件编号',
        dataIndex: 'fileid',
      },
      {
        title: '文件名称',
        dataIndex: 'oldname'
      },
      {
        title: '文件大小(B)',
        dataIndex: 'filesize'
      },
      {
        title: 'Action',
        dataIndex: 'action',
        slots:{customRender:'action'}
      }
    ];

   const mes=ref({});
   const messages=ref();
   const modalVisible=ref(false);
   const modalLoading=ref(false);
   const modalVisible2=ref(false);
   const modalLoading2=ref(false);
   const modalVisible3=ref(false);
   const modalLoading3=ref(false);
    const editmes=ref({
      messageid:"",
      mid: "",
      message_name: "",
      message_body: "",
      courseid: "",
      oldddl:""
    });
   const addmes=ref({
     mid: "",
     message_name: "",
     message_body: "",
     courseid: "",
   });
   const timeddl=ref();
    const timeddl2=ref();
    const timepick2=(time:any)=>{
      timeddl2.value=moment(time).format("YYYY-MM-DD HH:mm:ss");
    };

   const handleModalOk=()=>{
     modalLoading.value=true;
     let params = new URLSearchParams();
     params.append('mid', editmes.value.mid);
     params.append('ddl', timeddl2.value);
     params.append('message_name',editmes.value.message_name);
     params.append('message_body',editmes.value.message_body);
     params.append('accountid',user.value.countid);
     params.append('courseid',editmes.value.courseid)
     params.append('messageid',editmes.value.messageid)
     Axios.post("/message/change",params).then((response)=>{
       const data=response.data;
       if(data.ok){
         modalLoading.value=false;
         modalVisible.value=false;
         handleQuery();
       }
     })
   };

    const handleModalOk2=()=>{
      modalLoading2.value=true;
      setTimeout(()=>{
        modalLoading2.value=false;
        modalVisible2.value=false;
      },100)
    };

    const timepick=(time:any)=>{
      timeddl.value=moment(time).format("YYYY-MM-DD HH:mm:ss");
    };

    const handleModalOk3=()=>{
      modalLoading3.value=true;
      let params = new URLSearchParams();
      params.append('mid', addmes.value.mid);
      params.append('ddl', timeddl.value);
      params.append('message_name',addmes.value.message_name);
      params.append('message_body',addmes.value.message_body);
      params.append('accountid',user.value.countid);
      params.append('courseid',addmes.value.courseid)
      Axios.post("/message/add",params).then((response)=>{
        const data=response.data;
        if(data.ok){
          modalLoading3.value=false;
          modalVisible3.value=false;
          handleQuery();
        }else {
          message.warn(data.data)
          modalLoading3.value=false;
          modalVisible3.value=true;
        }
      })
    };

   const edit=(record:any)=>{
     timeddl2.value=""
     modalVisible.value=true;
     editmes.value.mid=record.mid;
     editmes.value.oldddl=record.ddl;
     editmes.value.messageid=record.messageid;
     editmes.value.message_name=record.message_name;
     editmes.value.message_body=record.message_body;
     editmes.value.courseid=record.courseid;
   };

    const show=(record:any)=>{
      modalVisible2.value=true;
      mes.value=Tool.copy(record);
    };

    const add=()=>{
      course.value="";
      addmes.value.mid="";
      addmes.value.message_name="";
      addmes.value.message_body="";
      getcourselist();
      modalVisible3.value=true;
    };

    const handledelete=(messageid:any)=>{
      const params={messageid:messageid}
      Axios.delete("/message/delete",{params}).then((response)=>{
        const data=response.data;
        if(data.ok){
          message.success("删除成功")
          handleQuery()
        }
        else{
          message.error("删除失败")
          handleQuery()
        }
      })
    };

    const courseid=ref();
    const course=ref();

    const getcourseid=(i:any)=>{
      courseid.value=i;
      addmes.value.courseid=courseid.value;
    };

    const getcourselist=()=>{
      Axios.get("/home/course/search", {
        params:{
          uname:user.value.uname,
        }
      }).then((response)=>{
        const data=response.data;
        course.value=data.data;
        courseid.value="";
      })
    };

   onMounted(()=>{
     handleQuery();
   });

    const columns = [
      {
        title: '作业编号',
        dataIndex: 'mid',
      },
      {
        title: '作业名称',
        dataIndex: 'message_name'
      },
      {
        title: '所属课程',
        dataIndex: 'courseName'
      },
      {
        title: '具体内容',
        dataIndex: 'message_body'
      },
      {
        title: '截止时间',
        dataIndex: 'ddl'
      },
      {
        title: 'Action',
        dataIndex: 'action',
        slots:{customRender:'action'}
      }
    ];

    const user =computed(()=>{
      return store.state.user
    });

   const handleQuery=()=>{
     Axios.get("/home/message/search", {
       params:{
         uname:user.value.uname,
         coursename:"",
       }
     }).then((response)=>{
       const data=response.data;
       messages.value=data.data;
     })
   };

   return {
     mes,
     messages,
     columns,
     modalVisible,
     modalLoading,
     modalVisible2,
     modalLoading2,
     modalVisible3,
     modalLoading3,
     course,
     addmes,
     editmes,
     modalLoading4,
     modalVisible4,
     filelist,
     filecolumns,

     edit,
     handleModalOk,
     handleModalOk2,
     handleModalOk3,
     show,
     add,
     getcourseid,
     moment,
     timepick,
     timepick2,
     handledelete,
     down,
     handleModalOk4,
     downloadfile,
   }
  }
});
</script>