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
        <h1>关联的课程</h1>
        <p>
          <a-button type="primary" size="large" @click="add()">
            新增课程
          </a-button>
        </p>
        <a-table :columns="columns"
                 :data-source="course">
          <template #action="{record}">
            <a-space size="small">
              <a-button type="primary" @click="edit(record)">
                编辑
              </a-button>
              <a-button type="danger" @click="handledelete(record.courseid)">
                删除
              </a-button>
            </a-space>
          </template>
        </a-table>
      </div>
    </a-layout-content>
  </a-layout>
  <a-modal
      title="修改课程信息"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
    <a-form :model="cour" :label-col="{span:6}" :wrapper-col="{span:18}">
      <a-form-item label="编号">
        <a-input v-model:value="cour.courseid" readonly/>
      </a-form-item>
      <a-form-item label="课程名称">
        <a-input v-model:value="cour.courseName"/>
      </a-form-item>
    </a-form>
  </a-modal>

  <a-modal
      title="新增课程"
      v-model:visible="modalVisible3"
      :confirm-loading="modalLoading3"
      @ok="handleModalOk3"
  >
    <a-form :model="cour" :label-col="{span:6}" :wrapper-col="{span:18}">
      <a-form-item label="课程名称">
        <a-input v-model:value="addcour.courseName"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import {defineComponent, ref, onMounted, computed} from 'vue';
import Axios from "axios";
import store from "@/store";
import {Tool} from "@/util/tool";
import {message} from "ant-design-vue";


export default defineComponent({
  name:'AdminCourse',
  setup(){
    const cour=ref({});
    const course=ref();
    const modalVisible=ref(false);
    const modalLoading=ref(false);
    const modalVisible3=ref(false);
    const modalLoading3=ref(false);
    const addcour=ref({
      courseName: "",
    })

    const handleModalOk=()=>{
      modalLoading.value=true;
      Axios.post("/course/change",cour.value).then((response)=>{
        const data=response.data;
        if(data.ok){
          modalLoading.value=false;
          modalVisible.value=false;
          handleQuery();
        }
      })
    };

    const user =computed(()=>{
      return store.state.user
    });

    const handleModalOk3=()=>{
      modalLoading3.value=true;
      let params = new URLSearchParams();
      params.append('courseName', addcour.value.courseName);
      params.append('accountId', user.value.countid);
      Axios.post("/course/add",params).then((response)=>{
        const data=response.data;
        if(data.ok){
          modalLoading3.value=false;
          modalVisible3.value=false;
          message.success("新增成功")
          handleQuery();
        }
        else {
          modalLoading3.value=false;
          modalVisible3.value=false;
          message.warn(data.data);
          handleQuery();
        }
      })
    };

    const edit=(record:any)=>{
      modalVisible.value=true;
      console.log(record);
      cour.value=Tool.copy(record);
    };

    const add=()=>{
      modalVisible3.value=true;
      cour.value={};
    };

    const handledelete=(courseId:any)=>{
      const params={courseId:courseId}
      Axios.delete("/course/delete",{params}).then((response)=>{
        const data=response.data;
        if(data.ok){
          message.success("删除成功");
          handleQuery();
        }
        else{
          message.error("删除失败");
          handleQuery();
        }
      })
    };

    onMounted(()=>{
      handleQuery();
    });

    const columns = [
      {
        title: '课程编号',
        dataIndex: 'courseid',
      },
      {
        title: '课程名称',
        dataIndex: 'courseName'
      },
      {
        title: 'Action',
        dataIndex: 'action',
        slots:{customRender:'action'}
      }
    ];
    const handleQuery=()=>{
      Axios.get("/course/search", {
        params:{
          countid:user.value.countid,
        }
      }).then((response)=>{
        const data=response.data;
        course.value=data.data;
      })
    };

    return {
      cour,
      course,
      columns,
      modalVisible,
      modalLoading,
      modalVisible3,
      modalLoading3,
      addcour,

      handleModalOk,
      edit,
      handleModalOk3,
      add,
      handledelete,
    }
  }
});
</script>