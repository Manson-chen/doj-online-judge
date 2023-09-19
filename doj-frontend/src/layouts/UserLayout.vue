<template>
  <div id="userLayout">
    <a-layout style="min-height: 100vh">
      <a-layout-header class="header">
        <a-space style="margin-top: 80px">
          <img src="../assets/logo.png" class="logo" />
          <div class="title">DOJ Online Judge System</div>
        </a-space>
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
      <a-layout-footer class="footer">
        <div>
          <icon-copyright />
          2023 DOJ在线编程 |
          <a-link href="https://beian.miit.gov.cn/" target="_blank"
            >粤ICP备2023104750号-1
          </a-link>
        </div>
      </a-layout-footer>
    </a-layout>
  </div>
</template>

<style scoped>
#userLayout {
  text-align: center;
  background: url(https://gw.alipayobjects.com/zos/rmsportal/FfdJeJRQWjEeGTpqgBKj.png)
    0% 0% / 100% 100%;
}

#userLayout .logo {
  width: 64px;
  height: 64px;
}

#userLayout .header {
  margin-top: 6px;
  color: #0e55c0;
  font-size: larger;
}

#userLayout .title {
  font-family: -apple-system, BlinkMacSystemFont, "Segoe WPC", "Segoe UI",
    "HelveticaNeue-Light", system-ui, "Ubuntu", "Droid Sans", sans-serif;
  font-size: larger;
}

#userLayout .content {
  padding: 10px;
  margin-bottom: 16px;
}

#userLayout .footer {
  padding: 16px;
  position: sticky;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}
</style>
<script>
import { IconCopyright } from "@arco-design/web-vue/es/icon";

export default {
  components: { IconCopyright },
};
</script>
