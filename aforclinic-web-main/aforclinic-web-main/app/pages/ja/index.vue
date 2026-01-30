<script setup lang="ts">
definePageMeta({
  layout: `default-i18n`,
  headThemeColor: `sensitive`,
  mainClass: `!pt-0`,
});

const runtimeConfig = useRuntimeConfig();
const route = useRoute();

useSeoMeta({
  // Basic
  title: `エイポクリニック`,
  description: `あなたの美しさを常に追求します。`,
  keywords:
    "カンナム皮膚科, アックジョン皮膚科, シンサ皮膚科, アックジョンロデオ皮膚科, アックジョンリフティング上手なクリニック, アックジョン施術が上手なクリニック, アックジョンリフティング, アックジョンスキンブースター, アックジョンボディ, アックジョンダイエット, アックジョン脱毛, ボイオレット, 脂肪分解注射, ボトックス, シワボトックス, エラボトックス, ゼオミンボトックス, スキンボトックス, トーニング, ピコシュア, リポット, クラリティ, 首のシワ, フィラー, ベロテロ, リジュラン, リジュランHB, ジュベルック, ボリューム, リジュネ, ラディアス, エクソソーム, 睡眠, ウルセラ, セルフ, オンダ, チタニウム, オンタニウム, バーチュ, PTT, ニキビ, シミ, 毛穴, アクアピール, LDM, ララピール, ほくろ除去, ウィゴビー, 多汗症, 栄養注射, 男性脱毛, 女性脱毛, ワキ, 鼻下, 顔全体, ふくらはぎ, 脚, 腕",

  // Open Graph
  ogTitle: `エイポクリニック`,
  ogDescription: `あなたの美しさを常に追求します。`,
  ogImage: `${runtimeConfig.public.siteBase}/images/logo.png`,
  ogImageWidth: `77`,
  ogImageHeight: `77`,
  ogUrl: `${runtimeConfig.public.siteBase}${route.fullPath}`,
  ogType: `website`,
  ogLocale: `ja_JP`,
  ogSiteName: "エイポクリニック",

  // Twitter
  twitterCard: `summary_large_image`,
  twitterTitle: `エイポクリニック`,
  twitterDescription: `あなたの美しさを常に追求します。`,
  twitterImage: `${runtimeConfig.public.siteBase}/images/logo.png`,
});
</script>

<template>
  <section class="relative">
    <a
      href="https://www.clipartkorea.co.kr/search/preview?cont_code=taim00090000447&total=&menu=m&hdn=AI%20%EB%B7%B0%E D%8B%B0%EB%AA%A8%EB%8D%B8&ska=2&sort=3&per=150&ad=off&page=1&nw=on&keyword=A1%20%EB%AA%A8%EB%8D%B8&vt=popup&popYn=Y"
      target="_blank"
    >
      <video
        src="/images/ja/bg_ch.mp4"
        width="768px"
        autoplay
        loop
        muted
        playsinline
        class="w-full cursor-pointer"
      />
      <div class="absolute bottom-12 left-6 text-black md:bottom-16 md:left-10">
        <p class="text-xs font-light uppercase tracking-wide md:text-sm">
          CUSTOMIZING BEAUTY SOLUTION
        </p>
        <h1 class="mt-1 font-serif text-3xl font-bold md:mt-2 md:text-5xl">
          AFOR CLINIC
        </h1>
        <p class="mt-3 text-xs leading-relaxed md:mt-4 md:text-sm">
          あなたの若さのために、<br />
          エイポクリニックは完璧の基準を掲げます。
        </p>
      </div>
    </a>
  </section>

  <section class="bg-[#E6E7EB]">
    <div class="space-y-4 px-4 py-8 md:px-12 md:py-12">
      <div class="space-y-2">
        <h2 class="text-2xl font-bold">ヒアルの神</h2>
        <p class="text-lg">ワンランク上の施術をご提供します</p>
      </div>
      <Carousel :autoplay="5000" :transition="500" :wrap-around="true">
        <Slide v-for="i in 3" :key="i">
          <img
            alt="シグネチャー1"
            class="w-full"
            :src="`/images/ja/signature-${i}_ch.jpg`"
            width="768px"
          />
        </Slide>

        <template #addons>
          <Pagination />
        </template>
      </Carousel>
    </div>
  </section>

  <section class="bg-[#E6E7EB]">
    <div class="space-y-4 px-4 py-8 md:px-12 md:py-12">
      <div class="space-y-2">
        <h2 class="text-2xl font-bold">中顔面短縮マスター</h2>
        <p class="text-lg">中顔面を短くすると、"小顔見え"が加速する。</p>
      </div>
      <div class="py-4">
        <img
          alt="中顔面短縮"
          src="/images/ja/popular-procedure_ch.png"
          class="w-full"
        />
      </div>
    </div>
  </section>

  <section class="bg-[#E6E7EB]">
    <div class="space-y-4 px-4 py-8 md:px-12 md:py-12">
      <div class="space-y-2">
        <h2 class="text-2xl font-bold">導入機器</h2>
        <p class="text-lg">人気のレーザー機器を取り揃えております</p>
      </div>
      <div class="py-4">
        <img
          alt="導入機器"
          src="/images/ja/popular-procedure_ch1.jpg"
          width="768px"
        />
      </div>
    </div>
  </section>

  <section class="bg-[#E6E7EB]">
    <div class="space-y-4 px-4 py-8 md:px-12 md:py-12">
      <div class="space-y-3">
        <h2 class="text-2xl font-bold">エイポクリニック 院長</h2>
        <p class="text-base leading-relaxed">
          長年の経験とノウハウをもとに、1:1のカスタマイズ施術と<br />
          お客様の安全を優先とする丁寧できめ細かな診療を提供いたします。
        </p>
      </div>

      <div class="py-4">
        <img
          alt="doctor"
          src="/images/ja/doctor_ch.png"
          class="w-full"
          width="768px"
        />
      </div>

      <ul
        class="relative mt-9 list-inside space-y-3 text-sm text-[#7c7268] md:text-xl"
      >
        <li class="font-semibold">代表院長</li>
        <li class="font-semibold">キム・グァンス</li>
        <li class="!mt-6">(現) エイポクリニック 代表院長</li>
        <li>(前) 魔法の手クリニック 院長</li>
        <li>(前) メイピュアクリニック 水西店・木洞店 院長</li>
        <li>(前) サンサンクリニック 新沙本店 院長</li>
        <li>(前) ジウゲクリニック 院長</li>
        <li class="!mt-6">大韓フィラー学会 正会員</li>
        <li>大韓美容整形レーザー医学会 正会員</li>
        <li>大韓レーザー皮膚毛髪学会 正会員</li>
        <li>大韓肥満美容学会 正会員</li>
        <li class="!mt-6">ジュビダームフィラーセミナー</li>
        <li>レスチレンフィラーハンズオン</li>
        <li>レディエッセフィラーハンズオン</li>
        <li>アラガン ボトックス セミナー</li>
        <li>ゼオミン ハンズオン</li>
      </ul>
    </div>
  </section>

  <section class="bg-[#E6E7EB]">
    <div class="space-y-4 px-4 py-8 md:px-12 md:py-12">
      <div class="space-y-2">
        <h2 class="text-2xl font-bold">エイポクリニックの雰囲気をのぞく</h2>
        <p class="text-lg">リラックスと癒しを感じられる快適な空間</p>
      </div>

      <div class="flex flex-col gap-4 pb-8 pt-6">
        <div class="w-full">
          <figure>
            <a
              href="/images/bf97d8c105b1cbe32cfca84a09253344c15e9ada0cda26f1fb54bd17d0d076e0.jpg"
              target="_blank"
            >
              <img
                alt="Interior 1"
                class="h-80 w-full rounded-3xl object-cover"
                src="/images/2ea922dd10087b377fb6f4ed8175c7133eab75c0dabbe0c5ddf879e924a2400e.webp"
              />
            </a>
          </figure>
        </div>
        <div class="flex w-full gap-4">
          <div class="flex w-1/3 flex-col gap-4">
            <div class="flex-1">
              <a
                href="/images/fc9f420cb402ff89c8dea72c27dfcb5ed29ff278b3b93778b3616f8a165353e3.jpg"
                target="_blank"
              >
                <img
                  alt="Interior 2"
                  class="h-60 w-full rounded-3xl object-cover"
                  src="/images/6dae012c3811a9fbe4b40869e10450b0af5192d6662cee1559c1e61585ff85d0.webp"
                />
              </a>
            </div>
            <div class="flex-1">
              <a
                href="/images/1ba0a195c8cb188ab004beaca8b05d81dbcd07e59e285f30cccced5ac75bcf5b.jpg"
                target="_blank"
              >
                <img
                  alt="Interior 3"
                  class="h-20 w-full rounded-3xl object-cover"
                  src="/images/8d8fddc031923ccd991ae8dfaba9d1198a2448864a70a43fee052ad7193a915d.webp"
                />
              </a>
            </div>
          </div>
          <div class="w-2/3">
            <a
              href="/images/a43deee868b3236a0c9067388e850883293e94d8e676090811372957b2205501.jpg"
              target="_blank"
            >
              <img
                alt="Interior 4"
                class="h-full w-full rounded-3xl object-cover"
                src="/images/aa274a9db2e07cc823233dcdf09ea098b4c1ae372ac3dfb097e2b98a2c02917c.webp"
              />
            </a>
          </div>
        </div>
        <div class="flex gap-4">
          <div class="w-2/3">
            <a
              href="/images/c7922be480a8e90ff06689e144a5b57371f26f725efd3c6790655e16ae1d24b8.jpg"
              target="_blank"
            >
              <img
                alt="Interior 5"
                class="h-48 w-full rounded-3xl object-cover"
                src="/images/21a84804911fd9b550e08a3476398dacea18bc9f3f18c0e08da30823f5b4f93f.webp"
              />
            </a>
          </div>
          <div class="w-1/3">
            <a
              href="/images/cd7dd804540fdfceebe8068a96f03ee7c0855c9ba760f59800b595fc44069c2f.jpg"
              target="_blank"
            >
              <img
                alt="Interior 6"
                class="h-48 w-full rounded-3xl object-cover"
                src="/images/66de1128c179f59cd821e685e321cff6ad379c043388466e9dbb662e4a1bbee7.webp"
              />
            </a>
          </div>
        </div>
      </div>
    </div>
  </section>

  <section class="bg-[#E6E7EB]">
    <div class="flex justify-center gap-4 px-4 py-12 md:px-12">
      <NuxtLink
        href="https://line.me/R/ti/p/@aforclinic"
        target="_blank"
        class="h-12 w-12 md:h-16 md:w-16"
      >
        <img alt="LINE" src="/images/ja/7-1_line.png" class="h-full w-full" />
      </NuxtLink>
      <NuxtLink
        href="https://www.instagram.com/aforclinic_jp/"
        target="_blank"
        class="h-12 w-12 md:h-16 md:w-16"
      >
        <img
          alt="Instagram"
          src="/images/ja/7-2_instagram.png"
          class="h-full w-full"
        />
      </NuxtLink>
      <NuxtLink
        href="https://x.com/afor_hihuka"
        target="_blank"
        class="h-12 w-12 md:h-16 md:w-16"
      >
        <img alt="X" src="/images/ja/7-3_X.png" class="h-full w-full" />
      </NuxtLink>
      <NuxtLink
        href="https://www.tiktok.com/@aforjp"
        target="_blank"
        class="h-12 w-12 md:h-16 md:w-16"
      >
        <img
          alt="TikTok"
          src="/images/ja/7-4_tiktok.png"
          class="h-full w-full"
        />
      </NuxtLink>
      <NuxtLink
        href="https://youtube.com/channel/UCpwpQtTdCbKKRcrmoKfOehPQ?si=ieTjnKuKnTvWN7J"
        target="_blank"
        class="h-12 w-12 md:h-16 md:w-16"
      >
        <img
          alt="YouTube"
          src="/images/ja/7-5_youtube.png"
          class="h-full w-full"
        />
      </NuxtLink>
    </div>
  </section>

  <section class="bg-white pt-12">
    <GoogleMap
      :lat="$config.public.naver.maps.lat"
      :lng="$config.public.naver.maps.lng"
      class="h-[250px] w-full md:h-[350px]"
      marker-title="エイポクリニック"
      language="ja"
    />
  </section>

  <section>
    <div class="space-y-4 px-4 py-8 md:px-12 md:py-12">
      <h2 class="text-xl font-semibold">Location</h2>
      <dl
        class="grid grid-cols-[auto_1fr] gap-4 gap-y-4 text-sm md:grid-cols-[10rem_1fr]"
      >
        <dt class="font-medium">住所</dt>
        <dd class="space-y-1 text-gray-500">
          <p>857, Nonhyeon ro , Gangnam gu , Seoul, Republic of Korea</p>
          <p>狎鴎亭駅4番出口徒歩3分</p>
          <div class="flex gap-6 pb-2 pt-4">
            <a
              class="hover:underline"
              href="https://www.google.com/maps/place/AFOR+CLINIC/data=!4m10!1m2!2m1!1z7JeQ7J207Y-s7J2Y7JuQ!3m6!1s0x357ca3001663e325:0x99f150caae08f7fd!8m2!3d37.5252412!4d127.0280596!15sCg_sl5DsnbTtj6zsnZjsm5BaEyIR7JeQ7J20IO2PrCDsnZjsm5CSAQ1kZXJtYXRvbG9naXN0mgEjQ2haRFNVaE5NRzluUzBWSlEwRm5UVU5KY0RoUU5VSlJFQUWqAVEQASoVIhHsl5DsnbQg7Y-sIOydmOybkCgJMh8QASIbHfWGVOEBqoVdhBZw8LqK1bVJ8WjdPkVmDU-KMhUQAiIR7JeQ7J20IO2PrCDsnZjsm5DgAQD6AQQIABAn!16s%2Fg%2F11wfl1r42m?entry=ttu&g_ep=EgoyMDI1MDUyMS4wIKXMDSoJLDEwMjExNDU1SAFQAw%3D%3D"
              target="_blank"
            >
              <img
                alt="グーグルマップ"
                class="me-2 inline"
                src="~/assets/icons/maps-icon.svg"
                width="20px"
                title="グーグルマップ"
              />
              グーグルマップ
            </a>
          </div>
        </dd>
        <dt class="font-medium">駐車場案内</dt>
        <dd class="space-y-1 text-gray-500">
          <p>バレット駐車を利用可能</p>
          <p>駐車料金支援</p>
        </dd>
        <dt class="font-medium">診療時間</dt>
        <dd class="space-y-1 text-gray-500">
          <div><span class="inline-block w-16">月, 木</span>: 10:30~20:00</div>
          <div><span class="inline-block w-16">火, 金</span>: 10:30~19:00</div>
          <div><span class="inline-block w-16">土</span>: 10:30~16:00</div>
          <div>
            <span class="inline-block w-16">昼 休 み</span>: 13:00~14:00
          </div>
          <div />
          <div class="space-y-1 pt-2 text-xs">
            <p>*土曜日は昼休みなし</p>
            <p>*水, 日曜日は休診</p>
          </div>
        </dd>
        <dt class="font-medium">
          <p>お問い合わせ</p>
          <p>可能時間</p>
        </dt>
        <dd class="text-gray-500">11:00 - 19:00 (韓国の時間基準)</dd>
      </dl>
    </div>
  </section>
</template>

<style lang="css" scoped>
.carousel :deep(.carousel__pagination) {
  @apply bottom-4 gap-4 md:bottom-6 md:gap-6;
}

.carousel :deep(.carousel__pagination-button) {
  @apply w-3 h-3 rounded-full bg-[#e29eb3];
}

.carousel :deep(.carousel__pagination-button--active) {
  @apply bg-[#9a445f];
}
</style>
