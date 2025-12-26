export interface HeaderProps {
  wide?: boolean;
}

export interface CalendarProps {
  borderless?: boolean;
  expanded?: boolean;
  timezone?: string;
  minDate?: string | Date;
  maxDate?: string | Date;
  disabledDates?: Date[];
}

export interface ModalProps {
  title?: string;
  confirmText?: string;
  cancelText?: string;
  showCancel?: boolean;
  noCloseOnBackdrop?: boolean;
  noHeaderClose?: boolean;
  noFooter?: boolean;
  fullscreen?: boolean;
  panelClass?: string;
}

//////////////////////////////////////////////////////////////

export interface ProductCardProps {
  productId: string;
  productName: string;
  description?: string;
  adjustedPrice: number;
  discountedPrice?: number;
  event?: boolean;
  until?: string;
}

export interface AppointmentCalendarProps {

}

export interface AppointmentAvailableTimesProps {
  selectedDate: Date;
}
