/**
    This file can be executed from shell using following command
    jshell /Users/alpha/git/MohanLearningGround/src/main/md/Tools/Java/concurrency_in_practice.jshell.java
**/

import java.util.concurrent.CompletableFuture;

ExecutorService executor0 = Executors. newWorkStealingPool();
ExecutorService executor1 = Executors. newWorkStealingPool();
//Completed when both of the futures are completed

CompletableFuture<String> waitingForAll = CompletableFuture.allOf( CompletableFuture.supplyAsync(() -> "first"),CompletableFuture.supplyAsync(() -> "second")).thenApply(ignored -> " is completed.");
CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> "Concurrency Refcard").thenCombine(waitingForAll, (first, second) -> first + second).thenAcceptAsync(result -> {System.out.println("Result is '" + result + "'.");});
//Implicitly using ForkJoinPool#commonPool as the //executor

//Generic handler
future.whenComplete((ignored, exception) -> { if (exception != null) exception.printStackTrace();});
//First blocking call - blocks until it is not finished. future.join();
future.thenRunAsync(() -> System.out.println("Current" + "thread is '" + Thread.currentThread().getName() + "'."));
//Executes in the current thread (which is main). .thenRun(() -> System.out.println("Current thread is '" + Thread.currentThread().getName() + "'."))
//Implicitly using ForkJoinPool#commonPool as the //executor

/exit
