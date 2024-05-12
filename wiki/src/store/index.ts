import { createStore } from 'vuex'

declare let SessionStorage: any;
const store =createStore({
  state: {
    user:SessionStorage.get("USER")||{}
  },
  mutations: {
    setUser(state,user){
      state.user=user;
      SessionStorage.set("USER",user);
    }
  },
  actions: {
  },
  modules: {
  }
})

export default store;
