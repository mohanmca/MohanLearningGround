//*[@id="rso"]/div/div
//*[@id="rso"]/div/div/div[1]/div/div/h3/a

//Google the results

function downloadURI(uri, name) {
  var link = document.createElement("a");
  link.download = name;
  link.href = uri;
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
  delete link;
}
function parseLinks() {
    var container = document.evaluate('//*[@id="rso"]/div/div', document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null)
    var results = Array.from(container.singleNodeValue.children)
    var resultLinks = results.map(childNode =>  document.evaluate('div/div/h3/a', childNode, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null))
    var result = resultLinks.map( node  => 
        {
            var o = new Object()
            o['href']    =  node.singleNodeValue.href
            o['text'] =   node.singleNodeValue.outerText
            return o;
        } 
    )
    console.table(result)
    var links = result.map(r => r.href)
    return links;
}
parseLinks().map(l => downloadURI(l,l))


/**
 *  ["https://www.jstor.org/stable/1572593", "https://tantalus.questu.ca/~rhoshino/talks/memory.pdf", "http://www.thsc.org/wp-content/uploads/2015/07/dia…our-childs-photographic-memory-woodlands-2015.pdf", "https://www.thsc.org/wp-content/uploads/2016/04/dianne-craft-training-photographic-memory.pdf", "https://bus.wisc.edu/~/media/bus/knowledge-experti…s/marketing/photo-memory-revision-final.pdf?la=en", "http://onlinelibrary.wiley.com/doi/10.1002/j.2326-1951.1970.tb00015.x/pdf", "http://www.joelkeller.com/Samples/north_jersey_morris_fall06_yamashita.PDF", "http://www.abilenecityhall.com/DocumentCenter/View/1165", "http://turnermagic.com/wp-content/uploads/jmt_memory_onepager_2010_red.pdf", "http://diannecraft.org/wp-content/uploads/2012/06/…ining-Your-Childs-Photographic-Memory-handout.pdf", "https://www.researchgate.net/profile/Mahesh_Gokhal…rowth-and-Development-at-under-graduate-level.pdf", "https://etd.ohiolink.edu/rws_etd/document/get/osu1316705103/inline", "http://www.dartneuroscience.com/press_releases/Connolly_J_1996.pdf", "https://link.springer.com/content/pdf/10.1038/225227a0.pdf", "http://www.penguin.com/static/packages/us/yreaders/camjansen/CamDownloadables/MemoryGame.pdf", "http://www.tandfonline.com/doi/pdf/10.1080/00223980.1969.10543063", "https://www.ncbi.nlm.nih.gov/pmc/articles/PMC1704671/pdf/canmedaj01462-0051a.pdf", "http://www.ics.uci.edu/~majumder/vispercep/travis.pdf", "https://www.khi.fi.it/5105938/20110616_PhotoArchives_London.pdf", "http://www.photographic-memory.org/dl2020/PhotographicMemoryV2.pdf", "http://unesdoc.unesco.org/images/0007/000791/079179eo.pdf", "https://www.lva.virginia.gov/news/press/PhotographyExhibitions.pdf", "https://www.computer.org/csdl/proceedings/afips/1958/5053/00/50530034.pdf", "http://www.yorku.ca/earmstro/courses/2nd_yr/memorization_and_practice.pdf", "http://www.itn.liu.se/grundutbildning/kurs/tnk067/literature/1.530751/Lecture5_ht2_handout.pdf", "http://www.triple-c.at/index.php/tripleC/article/viewFile/513/543", "http://www.acrwebsite.org/volumes/v43/acr_vol43_1019392.pdf", "https://www.cambridge.org/core/services/aop-cambri…hotographic-memory-ageing-and-the-life-course.pdf", "http://www.library.cornwall.on.ca/New_DP/docs/after_tulloch.pdf", "https://pdfs.semanticscholar.org/7a18/5f30498f817992fb432bb7de496e178be2f9.pdf", "http://www.chromatin3d.eu/images/training/courses/…our_Memory_How_It_Works_and_how_to_improve_it.pdf", "http://clasesdeapoyonuevo.s3.amazonaws.com/selectividad/soluciones/is37.pdf", "http://adadhannah.com/images/uploads/press_assets/2004_09_01_CV_Photo_65.pdf", "http://www.jillianfriedman.com/uploads/2/0/0/5/20057401/building_a_memory_palace.pdf", "http://www.eggspace.org/SHOWS/Photographic-Memory/Photographic-Memory-Art-Statements.pdf", "https://kunstgeschichte.univie.ac.at/fileadmin/use…/Institutsereignisse_2010/CONF_Londonprogramm.pdf", "https://www.seton.net/wp-content/uploads/2015/10/I…erns-Photographic-Memory-Catches-Filing-Error.pdf", "http://www.wrns100.co.uk/WBTL%20order%20form.pdf", "http://www.cell.com/current-biology/pdf/S0960-9822(13)01033-6.pdf", "http://static.ow.ly/docs/Photographic-Memory-PDF_4gTc.pdf", "http://nwfilmforum.org/downloads/0000/0364/photographicmemory_pk.pdf", "https://mesalitam.files.wordpress.com/2012/08/o-brien-how-to-develop-perfect-memory.pdf", "http://www.jalaltoufic.com/downloads/Jalal_Toufic,_Two_or_Three_Things_I_m_Dying_to_Tell_You.pdf", "http://journals.sagepub.com/doi/pdf/10.1177/0956797617694868", "http://www.sudhirneuro.org/files/small_book_on_term%20memory.pdf", "http://repository.upenn.edu/cgi/viewcontent.cgi?article=1048&context=curej", "http://integritymulticure.com/sites/all/files/pict…dderall-photographic-memory-vs-eidetic-memory.pdf", "http://www.ingentaconnect.com/content/routledg/cji…01/art00004?crawler=true&mimetype=application/pdf", "https://scholarship.rice.edu/bitstream/handle/1911/63177/article_RIP611_part8.pdf", "http://digitalcommons.colby.edu/cgi/viewcontent.cgi?article=1285&context=colbymagazine"]
 *  function downloadURI(uri, name) {
  var link = document.createElement("a");
  link.download = name;
  link.href = uri;
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
  delete link;
}
 **/