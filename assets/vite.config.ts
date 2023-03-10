import { fileURLToPath, URL } from "url";

import { defineConfig } from "vite";
import { viteStaticCopy as ViteStaticCopy } from "vite-plugin-static-copy";

export default defineConfig({
  build: {
    outDir: fileURLToPath(
      new URL("../src/main/resources/static", import.meta.url)
    ),
    emptyOutDir: true,
    lib: {
      entry: "src/index.ts",
      name: "assets",
      formats: ["iife"],
      fileName: () => "main.js",
    },
  },
});
