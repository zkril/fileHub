<template>
  <a-layout-header class="header">
    <div class="mylogo" >FileHub</div>
    <a-popconfirm
      title="确定推测出登录？"
      ok-text="是"
      cancel-text="否"
      @confirm="logout"
    >
      <a class="login-menu" v-show="user.countid">
        <span>退出登录</span>
      </a>
    </a-popconfirm>
    <a class="login-menu" v-show="user.countid">
      <span>您好：{{user.uname}}</span>
    </a>
    <a class="login-menu" v-show="!user.countid" @click="showLoginModal">
      <span>登录</span>
    </a>
    <a-menu
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="/home">
        <router-link to="/home">首页</router-link>
      </a-menu-item>
      <a-menu-item key="/about" >
        <router-link to="/about">关于我们</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/message" :style="user.countid?{}:{display:'none'}">
        <router-link to="/admin/message">管理</router-link>
      </a-menu-item>
    </a-menu>
  </a-layout-header>
  <a-modal
    title="登录"
    v-model:visible="loginModalVisible"
    :confirm-loading="loginModalLoading"
    @ok="login"
  >
    <a-form :label-col="{span: 6}" :wrapper-col="{span:18}">
      <a-form-item label="登录名">
        <a-input v-model:value="loginUser.loginName"/>
      </a-form-item>
      <a-form-item label="密码">
        <a-input v-model:value="loginUser.password" type="password"/>
      </a-form-item>
      <a-button type="primary" @click="checkin">
        没有账号？点击注册
      </a-button>
    </a-form>
  </a-modal>
  <a-modal
      title="注册"
      v-model:visible="loginModalVisible1"
      :confirm-loading="loginModalLoading1"
      @ok="adduser"
  >
    <a-form :label-col="{span: 6}" :wrapper-col="{span:18}">
      <a-form-item label="新登录名" placeholder="输入新用户名">
        <a-input v-model:value="addu.loginName"/>
      </a-form-item>
      <a-form-item label="设置密码" placeholder="输入密码">
        <a-input v-model:value="addu.password" type="password"/>
      </a-form-item>
      <a-form-item label="确认密码" placeholder="再次确认密码">
        <a-input v-model:value="addu.againpassword" type="password"/>
      </a-form-item>
      <a-form-item label="验证码" placeholder="输入指定验证码（可联系管理员获取）">
        <a-input v-model:value="addu.checkbox" type="password"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<style>
.mylogo {
  width: 120px;
  height: 31px;
  float: left;
  color: white;
  font-size: 18px;
}

.login-menu{
  float: right;
  color: white;
  padding-left: 10px;
}


</style>
<script lang="ts">
import {computed, defineComponent, ref} from 'vue';
import Axios from "axios";
import {message} from "ant-design-vue";
import store from "@/store";
import Router from "@/router";
export default defineComponent({
  name: 'the-header',
  setup(){


    const loginModalVisible=ref(false);
    const loginModalLoading=ref(false);
    const loginModalVisible1=ref(false);
    const loginModalLoading1=ref(false);
    const user=computed(()=>store.state.user);

    const loginUser=ref({
      loginName:"",
      password:"",
    });

    const addu=ref({
      loginName:"",
      password:"",
      againpassword:"",
      checkbox:"",
    });

    const showLoginModal=()=>{
      loginModalVisible.value=true;
    };

    const logout=()=>{
      store.commit("setUser",{});
      Router.push({path: '/home'});
    };

    const login=()=>{
      console.log("开始登录");
      loginModalLoading.value=true;
      let params = new URLSearchParams();
      params.append('uname', loginUser.value.loginName);
      params.append('password', loginUser.value.password);
      Axios.post('/login', params).then((response)=>{
        loginModalLoading.value=false;
        const data=response.data;
        if(data.ok){
          loginModalVisible.value=false;
          message.success("登录成功！");
          store.commit("setUser",data.data);
        } else {
          message.error(data.data);
        }
      });
    };

    const checkin=()=>{
      loginModalVisible.value=false;
      loginModalVisible1.value=true;
      addu.value.checkbox="";
      addu.value.againpassword="";
      addu.value.password="";
      addu.value.againpassword="";
    };

    const adduser=()=>{
      loginModalLoading1.value=true;
      if(addu.value.againpassword!=addu.value.password){
        message.error("两次输入的密码不一致");
        loginModalLoading1.value=false;
        return false;
      }
      if(addu.value.checkbox!="zkril"){
        message.error("验证码错误");
        loginModalLoading1.value=false;
        return false;
      }
      let params = new URLSearchParams();
      params.append('uname', addu.value.loginName);
      params.append('password', addu.value.password);
      Axios.post('/checkin', params).then((response)=>{
        loginModalLoading1.value=false;
        const data=response.data;
        if(data.ok){
          loginModalVisible1.value=false;
          message.success("注册成功！");
        } else {
          message.error(data.data);
        }
      });
    };

    return{
      loginModalVisible,
      loginModalLoading,
      loginModalVisible1,
      loginModalLoading1,

      loginUser,
      user,
      addu,

      showLoginModal,
      login,
      logout,
      checkin,
      adduser,
    }
  }
});
</script>
