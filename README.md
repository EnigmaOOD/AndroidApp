# اپلیکیشن جیرینگی

# معرفی پروژه
این پروژه یک ابزار مدیریت صورت حساب برای خریدهای گروهی است که به کاربران اجازه می‌دهد با وارد کردن ایمیل، گروه‌های خود را ایجاد کنند و دوستان خود را به آن اضافه کنند. سپس هر کاربر می‌تواند خریدهای انجام شده را با جزئیات مانند مبلغ خرید، نام خریداران و مصرف کنندگان و درصد های مصرف آن‌ها وارد کند. در نهایت، این برنامه برای هر کاربر میزان بدهکاری یا بستانکاری را نمایش می‌دهد. این ابزار به کاربران اجازه می‌دهد که به راحتی اطلاعات مالی خود را مدیریت کنند و همچنین برای گروه‌هایی که خریدهای مشترک دارند، بهبود هماهنگی و مدیریت دقیق تر خریدها را به ارمغان می‌آورد.

## تصاویر اپلیکیشن
<div>
 <img src="https://github.com/EnigmaOOD/AndroidApp/blob/master/ScreenShots/Screenshot_1695491987.png" width="19%"/>
 <img src="https://github.com/EnigmaOOD/AndroidApp/blob/master/ScreenShots/Screenshot_1695491994.png" width="19%"/>
 <img src="https://github.com/EnigmaOOD/AndroidApp/blob/master/ScreenShots/Screenshot_1695492267.png" width="19%"/>
 <img src="https://github.com/EnigmaOOD/AndroidApp/blob/master/ScreenShots/Screenshot_1695492005.png" width="19%"/>
 <img src="https://github.com/EnigmaOOD/AndroidApp/blob/master/ScreenShots/Screenshot_1695492164.png" width="19%"/>
</div>


## هدف پروژه 
این پروژه به هدف یادگیری مفاهیم درس طراحی شی‌گرا، به عنوان پروژه‌ی این درس و تحت اپلکیشن اندروید پیاده سازی شده است.
### اپلیکیشن
اپلیکیشن این پروژه با زبان Kotlin و به کمک ابزار jetpack compose برای سیستم عامل اندروید 6 به بالا پیاده سازی شده است. 
### سرور
سرور این پروژه با استفاده از جنگو(Django) و جنگو رست فریم‌ورک(Django REST framework)پیاده‌ سازی شده است.

## تیم توسعه اپلیکیشن و طراحی رابط کاربری
* محمد فرزان: طراحی UI/UX - مدیریت تسک‌ها - پیاده‌سازی اپلیکیشن
* نوریه سادات مدنیان: طراحی UI/UX - طراحی لوگو اپلیکیشن - پیاده‌سازی اپلیکیشن

  
<a href="https://www.figma.com/file/25DBpvpiSYGlJPKcVtGDAQ/Untitled?type=design&node-id=0%3A1&mode=design&t=8OWLPlW4SYhwKNTP-1" target="_blank">مشاهده رابط کاربری اپلیکیشن در فیگما</a>

## تیم توسعه سرور
* زهرا رمضانی
* مریم شفیع‌زادگان
* فاطمه شفیعی اردستانی
  
 <a href="https://github.com/EnigmaOOD/Enigma_BackEnd" target="_blank">ریپازیتوری سرور</a>
 
## قابلیت‌ها
* تشکیل گروه دنگی با امکان ثبت دسته‌بندی
* ثبت خرید با امکان ثبت سهم خریدار ها و مصرف کنندگان بصورت دقیق یا نسبی و تعیین دسته‌بندی برای خرید
* محاسبه‌ی سهم هر فرد در گروه
* فیلتر و مرتب سازی خرید های یک گروه براساس قیمت و خریدهای شما
  
## تست‌ها 
### تست رابط کاربری(UI Test)

35 تست برای سناریوهای مختلف نوشته شد که نتیجه‌ی آن در تصویر زیر نمایش داده شده است:

![android ui test](https://github.com/EnigmaOOD/AndroidApp/assets/60168299/239e6cb5-895c-490a-be0c-976b6c295f61)

### تست واحد(Unit Test)
97 تست نوشته شد که نتایج و کاوریج آن‌ها در تصاویر زیر نمایش داده شده است:
<img src="ScreenShots/unit test.png" />
![android unit test coverage](https://github.com/EnigmaOOD/AndroidApp/assets/60168299/c22c6763-da4a-4a91-9374-6b6272a9092f)

### جلوگیری از مهندسی معکوس و دیکامپایل از طریق مبهم سازی کد(Code Obfuscation)
برای جلوگیری از مهندسی معکوس و دیکامپایل از ابزار Enigma استفاده شد. این ابزار کدها و رشته‌ها را در نسخه Release مبهم می‌کند. به این صورت که در تصویر مشاهده می‌کنید نام کلاس‌ها و توابع رمز شده است و توسط انسان قابل فهم نیست.
![Code Obfuscation](https://github.com/EnigmaOOD/AndroidApp/assets/60168299/08343081-df58-45aa-afb5-34312695ef8c)
