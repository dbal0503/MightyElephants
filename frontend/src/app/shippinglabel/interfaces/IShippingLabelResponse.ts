export interface ShippingLabelResponse {
  shippingLabelId: number;
  shippingType: string;
  weight: number;
  origin: string;
  sender: string;
  recipient: string;
  destination: string;
  trackingNumber: string;
}
