## What are example for side-effect

1. Data fetching, setting up a subscription, and manually changing the DOM
2. There are two common kinds of side effects in React components: those that don’t require cleanup, and those that do.
3. We have to duplicate the code between these two lifecycle methods in class.
   1. componentDidUpdate (state-update after mounting) and componentDidMount 
   2. useEffect - alternative way using functional style



## Examples of side-effect without cleanup

Running some additional code after React has updated the DOM. Network requests, manual DOM mutations, and logging are common examples of effects that don’t require a cleanup.

## Effects with Cleanup

1. Setting up a subscription to some external data source. 
2. In that case, it is important to clean up so that we don’t introduce a memory leak!

```java
componentDidMount() {
    ChatAPI.subscribeToFriendStatus(this.props.friend.id,this.handleStatusChange); 
}  
componentWillUnmount() {
    ChatAPI.unsubscribeFromFriendStatus(this.props.friend.id, this.handleStatusChange); 
}
```

## What is the usage of UseEffect?

1. The Effect Hook lets you perform side effects in function components:
2. ```useEffect(() => {  document.title = `You clicked ${count} times`;  });```


## Effect behaviour

1. Each effect “belongs” to a particular render.
2. effects scheduled with useEffect don’t block the browser from updating the screen.
3. In the uncommon cases where they do (such as measuring the layout), there is a separate useLayoutEffect Hook with an API identical to useEffect.


## How to create cleanup in Effect (un-subscribe)

1. When exactly does React clean up an effect? React performs the cleanup when the component unmounts
2. React also cleans up effects from the previous render before running the effects next time.
3. We could return an arrow function or call it something different instead of cleanup.

```java
useEffect(() => {    
    function handleStatusChange(status) {     
        setIsOnline(status.isOnline);    }  
        ChatAPI.subscribeToFriendStatus(props.friend.id, handleStatusChange);  
        // Specify how to clean up after this effect:   
         return function cleanup() {
            ChatAPI.unsubscribeFromFriendStatus(props.friend.id, handleStatusChange);  
         };
    });
```

##  Hooks order
1. React will apply every effect used by the component, in the order they were specified.

## Hooks dependency and net-effect only when changed

1. You can tell React to skip applying an effect if certain values haven’t changed between re-renders. 
   1. To do so, pass an array as an optional second argument to useEffect:
2. If you want to run an effect and clean it up only once (on mount and unmount), you can pass an empty array ([]) as a second argument.
3. This tells React that your effect doesn’t depend on any values from props or state, so it never needs to re-run. 

## How to make it anki
* mdanki React_anki.md React_anki.md.apkg --deck "Everything::UnderSun::ReactJS::Hooks"
