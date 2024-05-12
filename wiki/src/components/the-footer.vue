<template>
  <a-layout-footer style="text-align: center">
    filehub <span v-show="user.countid">,欢迎：{{user.uname}}</span> ©2024 Created by 少年少女明日会不会破防
  </a-layout-footer>
</template>


<script lang="ts">
import {defineComponent,computed} from 'vue';
import store from "@/store";

export default defineComponent({
  name: 'the-footer',
  setup(){
    const user =computed(()=>{
      return store.state.user
    })
    return {
      user
    }
  }
});
</script>