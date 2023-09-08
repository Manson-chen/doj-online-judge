<template>
  <a-row id="globalHeader" align="center" :wrap="false">
    <a-col flex="auto">
      <div>
        <a-menu
          mode="horizontal"
          :selected-keys="selectedKeys"
          @menu-item-click="doMenuClick"
        >
          <a-menu-item
            key="0"
            :style="{ padding: 0, marginRight: '38px' }"
            disabled
          >
            <div class="title-bar">
              <img class="logo" src="../assets/logo.png" />
              <div class="title">D OJ</div>
            </div>
          </a-menu-item>
          <a-menu-item v-for="item in visibleRoutes" :key="item.path">
            {{ item.name }}
          </a-menu-item>
        </a-menu>
      </div>
    </a-col>
    <a-col flex="100px">
      <div style="margin-right: 100px">
        <a-dropdown trigger="hover" :hide-on-select="false">
          <a-button type="text" long v-if="store.state.user.loginUser.userName"
            >{{ store.state.user.loginUser.userName }}
          </a-button>
          <a-button type="text" long v-else>未登录</a-button>
          <template #content>
            <a-doption v-if="userInfo.userName !== '未登录'">
              <a-space direction="vertical" size="large">
                <a-input
                  :style="{
                    width: '100px',
                    background: 'none',
                    paddingLeft: 0,
                  }"
                  :default-value="userInfo.userName"
                  placeholder="Please enter something"
                  @blur="loadData"
                  v-model="userInfo.userName"
                  @pressEnter="updateUserInfo"
                >
                  <template #prefix>
                    <icon-edit @click="updateUserInfo" />
                  </template>
                </a-input>
              </a-space>
            </a-doption>
            <a-doption
              class="info"
              @click="logout"
              v-if="userInfo.userName === '未登录'"
            >
              <template #icon>
                <icon-user-add />
              </template>
              登录
            </a-doption>
            <a-doption class="info" @click="logout" v-else>
              <template #icon>
                <icon-poweroff />
              </template>
              <div style="margin-left: 3px">退出</div>
            </a-doption>
          </template>
        </a-dropdown>
      </div>
    </a-col>
  </a-row>
</template>

<script setup lang="ts">
import { routes } from "../router/routes";
import { useRoute, useRouter } from "vue-router";
import { onMounted, reactive, watchEffect } from "vue";
import { computed, ref } from "vue";
import { useStore } from "vuex";
import checkAccess from "@/access/checkAccess";
import AccessEnum from "@/access/accessEnum";
import ACCESS_ENUM from "@/access/accessEnum";
import { UserControllerService, UserUpdateMyRequest } from "../../generated";
import message from "@arco-design/web-vue/es/message";
import {
  IconUser,
  IconUserAdd,
  IconEdit,
  IconPoweroff,
} from "@arco-design/web-vue/es/icon";

const router = useRouter();

const store = useStore();
const loginUser = store.state.user.loginUser;

// 展示在菜单的路由数组
const visibleRoutes = computed(() => {
  return routes.filter((item, index) => {
    if (item.meta?.hideInMenu) {
      return false;
    }
    // 根据权限过滤菜单
    // 注意传入的loginUser不能是写死的
    if (!checkAccess(store.state.user.loginUser, item.meta?.access as string)) {
      return false;
    }
    return true;
  });
});

const userInfo = reactive({
  userName: "",
} as UserUpdateMyRequest);

const loadData = async () => {
  userInfo.userName = store.state.user.loginUser.userName;
};

watchEffect(() => {
  userInfo.userName = store.state.user.loginUser.userName;
});

onMounted(() => {
  loadData();
});

const updateUserInfo = async () => {
  const res = await UserControllerService.updateMyUserUsingPost(userInfo);
  if (res.code === 0) {
    // store.state.user.loginUser.userName = userInfo.userName;
    await store.dispatch("user/getLoginUser");
    message.success("更改用户信息成功");
  } else {
    message.error("更改用户信息失败，" + res.message);
  }
};

// 默认主页
const selectedKeys = ref(["/"]);

// 路由跳转时，更新选中的菜单项
router.afterEach((to, from, failure) => {
  selectedKeys.value = [to.path];
});

setTimeout(() => {
  store.dispatch("user/getLoginUser", {
    userName: "jiandongAdmin",
    userRole: ACCESS_ENUM.ADMIN,
  });
}, 3000);

const doMenuClick = (key: string) => {
  router.push({
    path: key,
  });
};

const logout = async () => {
  const res = await UserControllerService.userLogoutUsingPost();
  if (res.code === 0) {
    message.success("退出登录成功");
    await store.dispatch("user/setLoginUser", null);
    router.push({
      path: "/user/login",
    });
  } else {
    router.push({
      path: "/user/login",
    });
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.title-bar {
  display: flex;
  align-items: center;
}

.title {
  color: rgba(14, 85, 192, 0.86);
  margin-left: 16px;
  font-weight: bold;
}

.logo {
  height: 48px;
}

.info {
  /*display: flex;*/
  /*align-items: center;*/
}
</style>
