# 守护线程Daemon

1. 设置守护线程必须在start方法之前设置会报`IllegalThradStateException`，但是程序还是会继续执行，但是该线程并不会随着主线程的结束而结束了