/**
 *  Copy below code to register in any SPA application
 */

export default function recorder(document) {
  const t0 = Date.now();
  const events = [];

  const record = ({ x, y, pageX, pageY, screenX, screenY }) => {
    let event = {
      time: getCurrentElapsedTime(),
      x,
      y,
      pageX,
      pageY,
      screenX,
      screenY
    };
    console.log(event);
    events.push(event);
  };

  const dump = () => {
    console.log(JSON.stringify(events));
  };

  function getCurrentElapsedTime() {
    return Date.now() - t0;
  }

  // document.getElementsByTagName("body").onmousemove = record;
  // document.getElementsByTagName("body").onclick = dump;

   document.getElementById("root").onmousemove = record;
   document.getElementById("root").onclick = dump;
    
}
