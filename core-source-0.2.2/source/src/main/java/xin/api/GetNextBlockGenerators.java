// /******************************************************************************
//  * Copyright © 2013-2016 The Nxt Core Developers.                             *
//  *                                                                            *
//  * See the AUTHORS.txt, DEVELOPER-AGREEMENT.txt and LICENSE.txt files at      *
//  * the top-level directory of this distribution for the individual copyright  *
//  * holder information and the developer policies on copyright and licensing.  *
//  *                                                                            *
//  * Unless otherwise agreed in a custom licensing agreement, no part of the    *
//  * Nxt software, including this file, may be copied, modified, propagated,    *
//  * or distributed except according to the terms contained in the LICENSE.txt  *
//  * file.                                                                      *
//  *                                                                            *
//  * Removal or modification of this copyright notice is prohibited.            *
//  *                                                                            *
//  ******************************************************************************/
//
// package xin.api;
//
// import xin.Block;
// import xin.Constants;
// import xin.Hub;
// import xin.Xin;
// import org.json.simple.JSONArray;
// import org.json.simple.JSONObject;
// import org.json.simple.JSONStreamAware;
//
// import javax.servlet.http.HttpServletRequest;
// import java.util.Iterator;
//
// public final class GetNextBlockGenerators extends APIServlet.APIRequestHandler {
//
//     static final GetNextBlockGenerators instance = new GetNextBlockGenerators();
//
//     private GetNextBlockGenerators() {
//         super(new APITag[]{APITag.FORGING});
//     }
//
//     @Override
//     protected JSONStreamAware processRequest(HttpServletRequest req) {
//
//
//         Block curBlock = Xin.getBlockchain().getLastBlock();
//         if (curBlock.getHeight() < Constants.TRANSPARENT_FORGING_BLOCK_7) {
//             return JSONResponses.FEATURE_NOT_AVAILABLE;
//         }
//
//
//         JSONObject response = new JSONObject();
//         response.put("time", Xin.getEpochTime());
//         response.put("lastBlock", Long.toUnsignedString(curBlock.getId()));
//         JSONArray hubs = new JSONArray();
//
//         int limit;
//         try {
//             limit = Integer.parseInt(req.getParameter("limit"));
//         } catch (RuntimeException e) {
//             limit = Integer.MAX_VALUE;
//         }
//
//         Iterator<Hub.Hit> iterator = Hub.getHubHits(curBlock).iterator();
//         while (iterator.hasNext() && hubs.size() < limit) {
//             JSONObject hub = new JSONObject();
//             Hub.Hit hit = iterator.next();
//             hub.put("account", Long.toUnsignedString(hit.hub.getAccountId()));
//             hub.put("minFeePerByteTQT", hit.hub.getMinFeePerByteTQT());
//             hub.put("time", hit.hitTime);
//             JSONArray uris = new JSONArray();
//             uris.addAll(hit.hub.getUris());
//             hub.put("uris", uris);
//             hubs.add(hub);
//         }
//
//         response.put("hubs", hubs);
//         return response;
//     }
//
// }
