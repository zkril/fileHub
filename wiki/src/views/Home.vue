<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0 }"
    >
      <p>
        <a-form layout="inline" :model="param">
          <a-form-item label="学委姓名" @change="getcourselist">
            <a-input v-model:value="param.uname" placeholder="输入学委全名"/>
          </a-form-item>
          <a-form-item label="课程名称">
            <a-select :showSearch="true"  placeholder="选择学委对应的课程" @change="getcoursename">
              <a-select-option v-for="i in course" :key="i.courseid" :value="i.courseName">
                {{i.courseName}}
              </a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="handleQuery()">
              查询
            </a-button>
          </a-form-item>
        </a-form>
      </p>
      <a-list  size="large"  :data-source="messages">
        <template #renderItem="{ item }">
        <a-list-item key="item.message_name">
          <a-list-item-meta :description="'作业详情：' +item.message_body">
            <template #title>
              作业标题：{{ item.message_name }}
            </template>
          </a-list-item-meta>
          <div>
            <span>学委名称：{{item.uname}}</span>
            <a-divider type="vertical"></a-divider>
            <span>课程名称：{{item.courseName}}</span>
            <a-divider type="vertical"></a-divider>
            <span>结束时间：{{item.ddl}}</span>
            <a-divider type="vertical"></a-divider>
            <a-button type="primary" @click="upload(item.messageid)">
              上传
            </a-button>
          </div>
        </a-list-item>
        </template>
      </a-list>
    </a-layout-content>
  </a-layout>
  <a-modal
      title="上传作业"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
    <a-upload
        :multiple="false"
        v-model:fileList="downloadFiles"
        :customRequest="downloadFilesCustomRequest"
    >
      <a-button> <a-icon type="upload" /> 上传文件 </a-button>
    </a-upload>
  </a-modal>
</template>



<script lang="ts">
import {defineComponent, onMounted, ref,} from 'vue';
import Axios from "axios";
import {message} from "ant-design-vue";

export default defineComponent({
  name: 'Home',

  setup() {
    const messageid=ref();
    const downloadFiles=ref([] as any);

    const downloadFilesCustomRequest=(data:any)=> {
      message.success("文件开始上传");
      saveFile(data);
    };

    const saveFile=(filedata:any)=>{
      const formData = new FormData();
      formData.append('file', filedata.file);
      formData.append('messageid', messageid.value);
      console.log(filedata.file);
      Axios.post('/home/upload', formData).then((res) => {  // 发送http请求
        const data=res.data;
        if (data.ok){
          message.success("文件上传成功");
          let file = fileFormatter(filedata.file);
          downloadFiles.value=[file];
        } else {
          message.error("文件上传失败");
        }
      })
    };

    const fileFormatter=(data:any)=> {
      return {
        uid: data.uid,    // 文件唯一标识，建议设置为负数，防止和内部产生的 id 冲突
        name: data.name,   // 文件名
        status: 'done', // 状态有：uploading done error removed
      }
    }

    const modalVisible=ref(false);
    const modalLoading=ref(false);

    const upload=(messageid2:any)=>{
      downloadFiles.value=[];
      modalVisible.value=true;
      messageid.value=messageid2;
    };

    const handleModalOk=()=>{
      modalVisible.value=false;
      messageid.value="";
    }

    const messages=ref();
    const param=ref({
      uname:"",
    });
    const coursename=ref();
    const course=ref();

    const getcoursename=(i:any)=>{
      coursename.value=i;
    };

    const getcourselist=()=>{
      Axios.get("/home/course/search", {
        params:{
          uname:param.value.uname,
        }
      }).then((response)=>{
        const data=response.data;
        course.value=data.data;
        coursename.value="";
      })
    };

    const handleQuery=()=>{
      Axios.get("/home/message/search", {
        params:{
          uname:param.value.uname,
          coursename:coursename.value,
        }
      }).then((response)=>{
        const data=response.data;
        messages.value=data.data;
      })
    };

    onMounted(()=>{
      Axios.get("/home/message/search",{
        params:{
          uname:"",
          coursename:"",
        }
      }).then((response)=>{
        console.log(response);
        const data=response.data;
        messages.value=data.data;
      })
    });

    return {
      File,
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      downloadFiles,
      messages,
      param,
      course,
      modalVisible,
      modalLoading,

      downloadFilesCustomRequest,
      getcourselist,
      getcoursename,
      handleQuery,
      upload,
      handleModalOk,
    }
  }
});
</script>
