<template>
  <div id="userLoginView">
    <h2 style="margin-bottom: 32px; color: rgba(14, 85, 192, 0.86)">
      用户登录
    </h2>
    <a-form
      style="max-width: 480px; margin: 0 auto"
      :model="form"
      label-align="left"
      auto-label-width
      @submit="handleSubmit"
    >
      <a-form-item field="userAccount" label="账号">
        <a-input
          v-model="form.userAccount"
          placeholder="请输入账号：jiandong"
        />
      </a-form-item>
      <a-form-item field="userPassword" tooltip="密码不少于 8 位" label="密码">
        <a-input-password
          v-model="form.userPassword"
          placeholder="请输入密码：abc123456"
        />
      </a-form-item>
      <a-form-item>
        <div class="button-container">
          <a-button type="primary" html-type="submit" style="width: 180px"
            >登录
          </a-button>
        </div>
        <div style="margin-left: 140px; display: flex">
          <a-button type="text" @click="toRegister">新用户注册？</a-button>
        </div>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import { reactive } from "vue";
import { UserControllerService, UserLoginRequest } from "../../../generated";
import message from "@arco-design/web-vue/es/message";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

/**
 * 表单信息
 */
const form = reactive({
  userAccount: "",
  userPassword: "",
} as UserLoginRequest);

const router = useRouter();
const store = useStore();

/**
 * 提交表单
 * @param data
 */
const handleSubmit = async () => {
  const res = await UserControllerService.userLoginUsingPost(form);
  // 登录成功，跳转到主页
  if (res.code === 0) {
    await store.dispatch("user/getLoginUser");
    router.push({
      path: "/",
      replace: true,
    });
  } else {
    message.error("登录失败，" + res.message);
  }
};

const toRegister = async () => {
  router.push({
    path: "/user/register",
    replace: true,
  });
};
</script>

<style scoped>
#userLoginView {
  /*margin-top: 200px;*/
}

.button-container {
  /*display: flex;*/
  /*justify-content: flex-end;*/
  /*position: relative;*/
  /*margin: 0 auto;*/
  /*justify-content: flex-end;*/
}
</style>
