<template>
  <div id="basicLayout">
    <a-layout style="min-height: 100vh">
      <a-layout-header class="header">
        <GlobalHeader></GlobalHeader>
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
#basicLayout {
}

#basicLayout .header {
  margin-bottom: 16px;
  box-shadow: #eee 1px 1px 5px;
}

#basicLayout .content {
  padding: 20px;
  background: linear-gradient(to right, #fefefe, #fff);
  margin-bottom: 16px;
}

#basicLayout .footer {
  background: rgb(255, 255, 255);
  padding: 16px;
  position: sticky;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}
</style>
<script>
import GlobalHeader from "@/components/GlobalHeader";
import { IconCopyright } from "@arco-design/web-vue/es/icon";

export default {
  components: { GlobalHeader, IconCopyright },
};
</script>
